package com.github.smartenergysystem.price.collector.priceCollector;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.net.NoRouteToHostException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;
import java.util.function.Predicate;

@Service
public class PriceCollectorService {

    private static final int DAY_OFFSET = 5;
    @Value("${entsoe.securityToken}")
    private String securityToken;

    @Value("${entsoe.area}")
    private String area;

    private Logger logger = LoggerFactory.getLogger(PriceCollectorService.class);

    @Autowired
    PriceRepository repository;

    public List<DayAheadPricePoint> getDayAheadPricePoints(Date startDate, Date endDate) throws URISyntaxException, ParseException {
        List<DayAheadPricePoint> replyList = new LinkedList<>();
        List<DayAheadPricePoint> priceList = null;
        try {
            priceList = requestPriceData(startDate, endDate);
        }catch (ResourceAccessException e) {
            logger.error("Could not connect to entsoe, trying to get the date from local db",e);
            priceList = new LinkedList<>(); //Empty list
        }
        //Save requested prices in DB
        priceList.forEach(pricePoint->repository.save(pricePoint));

        List<Date> requiredHours = getRequiredDates(startDate, endDate);

        //Add each necessary price point to the reply
        priceList.forEach(pricePoint -> {
            if (requiredHours.contains(pricePoint.getTime())) {
                replyList.add(pricePoint);
            }
        });

        List<Date> missingHours = calculateMissingHours(requiredHours, priceList);
        //Request missing Hours from DB as A4.2 requires
        Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        for(Date date : missingHours){
            calendar.setTime(date);
            Optional<DayAheadPricePoint> dayAheadPricePointOptional = repository.findOldPriceFromSameHour(calendar.get(Calendar.HOUR_OF_DAY));
            if(dayAheadPricePointOptional.isPresent()){
                DayAheadPricePoint oldDayAheadPricePoint = dayAheadPricePointOptional.get();
                oldDayAheadPricePoint.setOldValue(true);
                oldDayAheadPricePoint.setTime(date);
                replyList.add(oldDayAheadPricePoint);
            }else{
                logger.error("There is no value for "+date + " on entsoe or in our database");
                throw new NotInDatebaseException();
            }
        }
        return replyList;
    }

    private List<Date> calculateMissingHours(List<Date> requiredHours, List<DayAheadPricePoint> priceList) {
        List<Date> missingHours = new LinkedList<>(requiredHours);
        //Is not the fastest solution because we iterate over priceList for each element of missingHours
        Predicate<Date> isContaintInPriceList = date -> priceList.stream().map(DayAheadPricePoint::getTime).anyMatch(priceDate -> priceDate.equals(date));
        missingHours.removeIf(isContaintInPriceList);
        return missingHours;
    }

    private List<DayAheadPricePoint> requestPriceData(Date startDate, Date endDate) throws URISyntaxException, ParseException {

        //Request a longer duration because the data is returned in a series of a full day.
        Calendar calender = getUtcCalendar(startDate);
        calender.add(Calendar.DATE, -DAY_OFFSET);
        Date previousDay = calender.getTime();

        calender.setTime(endDate);
        calender.add(Calendar.DATE, DAY_OFFSET);
        Date nextDayAfterEndDate = calender.getTime();
        logger.info("Request data form:" + previousDay + " to:" + nextDayAfterEndDate);
        PriceCollector priceCollector = new PriceCollector(securityToken);
        return priceCollector.collectPrices(PriceCollector.DocumentTypes.DAY_AHEAD_PRICES, area, previousDay, nextDayAfterEndDate);
    }

    private Calendar getUtcCalendar(Date startDate) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeZone(TimeZone.getTimeZone("UTC"));
        calender.setTime(startDate);
        return calender;
    }

    /**
     * Calculates a list of hours which must be in the answer
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private List<Date> getRequiredDates(Date startDate, Date endDate) {
        Calendar calender = getUtcCalendar(startDate);
        Date tempDate = startDate;
        List<Date> requiredHours = new LinkedList<>();
        requiredHours.add(startDate);
        while (tempDate.before(endDate)) {
            calender.add(Calendar.HOUR, 1);
            tempDate = calender.getTime();
            requiredHours.add(tempDate);
        }
        return requiredHours;
    }
}
