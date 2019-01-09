package com.github.smartenergysystem.price.collector.priceCollector;

import com.github.smartenergysystem.price.collector.priceCollector.xml.PublicationMarketDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PriceCollector {


    private static final String ZERO_MINUTES = "00";
    private static final String SUPPORTED_CURRENCY = "EUR";
    private static final String SUPPORTED_PRICE_MEASURE_UNIT = "MWH";
    private static final int POINT_HOUR_OFFSET = 1;
    private String securityToken;
    private Logger logger = LoggerFactory.getLogger(PriceCollector.class);

    public enum DocumentTypes {
        DAY_AHEAD_PRICES("A44");

        private final String documentType;

        DocumentTypes(final String documentType) {
            this.documentType = documentType;
        }

        @Override
        public String toString() {
            return documentType;
        }
    }


    public PriceCollector(String securityToken) {
        this.securityToken = securityToken;
    }

    public List<DayAheadPricePoint> collectPrices(DocumentTypes documentType, String area, Date startTime, Date endTime) throws URISyntaxException, ParseException {
        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat customFormat = new SimpleDateFormat("yyyyMMddHH");
        customFormat.setTimeZone(utc);
        System.out.println(startTime.toString());
        String startDate = customFormat.format(startTime) + ZERO_MINUTES;
        String endDate = customFormat.format(endTime) + ZERO_MINUTES;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://transparency.entsoe.eu/api");
        builder.queryParam("securityToken", securityToken)
                .queryParam("in_Domain", area)
                .queryParam("out_Domain", area)
                .queryParam("documentType", documentType.toString())
                .queryParam("periodStart", startDate)
                .queryParam("periodEnd", endDate);
        logger.info("Request URI:" + builder.toUriString());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PublicationMarketDocument> response
                = restTemplate.getForEntity(builder.toUriString(), PublicationMarketDocument.class);
        PublicationMarketDocument publicationMarketDocument = response.getBody();
        //TODO add a custom exceptions with the reason why it fails
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            if (publicationMarketDocument.getTimeSeries().size() != 0) {
                LinkedList<DayAheadPricePoint> prices = new LinkedList<>();
                SimpleDateFormat isoDataFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                isoDataFormat.setTimeZone(utc);
                for (PublicationMarketDocument.TimeSeries timeSeries : publicationMarketDocument.getTimeSeries()) {
                    logger.info("Processing TimeSeries:"+timeSeries.getMRID());
                    if (timeSeries.getCurrencyUnitName().equals(SUPPORTED_CURRENCY) && timeSeries.getPriceMeasureUnitName().equals(SUPPORTED_PRICE_MEASURE_UNIT)) {
                        PublicationMarketDocument.TimeSeries.Period period = timeSeries.getPeriod();
                        String startTimeOfTimeSeries = period.getTimeInterval().getStart();
                        Date timeSeriesBaseDate = isoDataFormat.parse(startTimeOfTimeSeries);//)new Date(period.getTimeInterval().getStart());
                        Calendar calender = Calendar.getInstance();
                        calender.setTimeZone(utc);
                        calender.setTime(timeSeriesBaseDate);
                        logger.info("Start time as set:"+startTimeOfTimeSeries+ " Parsed time:"+calender.getTime());
                        //calender.add(Calendar.HOUR, -1);//First point is at base Date
                        period.getPoint().forEach(point -> {
                            Date timeOfPricePoint = calender.getTime();
                            float amount = point.getPriceAmount();
                            logger.info("Adding price point:"+timeOfPricePoint + " with amount:"+amount);
                            prices.add(new DayAheadPricePoint(timeOfPricePoint, amount));
                            calender.add(Calendar.HOUR_OF_DAY, POINT_HOUR_OFFSET);
                        });
                    } else {
                        logger.error("Response uses the wrong units");
                        return prices;
                    }
                }
                return prices;
            } else {
                logger.error("Got not data form entsoe");
            }
        } else {
            logger.error("HTTP Status not ok:" + response.getStatusCode().value());
        }
        return null;
    }

}
