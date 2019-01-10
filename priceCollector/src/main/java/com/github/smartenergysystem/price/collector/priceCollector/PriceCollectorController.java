package com.github.smartenergysystem.price.collector.priceCollector;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;
import java.util.function.Predicate;

@RestController
public class PriceCollectorController {

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
    private static final int DAY_OFFSET = 5;
    @Value("${entsoe.securityToken}")
    private String securityToken;

    @Value("${entsoe.area}")
    private String area;

    @Autowired
    PriceRepository repository;
    private Logger logger = LoggerFactory.getLogger(PriceCollectorController.class);

    /**
     * Solves A4.3
     * @param startDate
     * @param endDate
     * @return
     */
    @ApiOperation(value = "Inputs are always UTC and use the pattern " + DATE_PATTERN)
    @GetMapping("/prices")
    public ResponseEntity<List<DayAheadPricePoint>> getDayAheadPrices(@ApiParam(name = "startDate", value = "The start date", defaultValue = "2019-01-07T00:00Z")
                                                                      @RequestParam("startDate") @DateTimeFormat(pattern = DATE_PATTERN) Date startDate,
                                                                      @ApiParam(name = "endDate", value = "The end date", defaultValue = "2019-01-07T23:00Z")
                                                                      @RequestParam("endDate") @DateTimeFormat(pattern = DATE_PATTERN) Date endDate) {
        if (startDate.before(endDate)) {
            try {
                List<DayAheadPricePoint> replyList = new LinkedList<>();
                List<DayAheadPricePoint> priceList = requestPriceData(startDate, endDate);

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
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There is no value for "+date + " on entsoe or in our database");
                    }
                }
                return ResponseEntity.ok(replyList);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The request to entsoe could not be built, check your configuration");
            } catch (ParseException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The response from entsoe could be parsed");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The enddate is before the startDate");
        }
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
