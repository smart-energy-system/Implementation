package com.github.smartenergysystem.price.collector.priceCollector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
public class ScheduledTasks {


    private Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    PriceCollectorService priceCollectorService;

    @Scheduled(cron ="${autofetch.cron.expression}")
    public void reportCurrentTime() {
        logger.info("Fetch Day Ahead prices");
        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE,-1);
        startDate = calendar.getTime();
        calendar.add(Calendar.DATE,2);
        Date endDate = calendar.getTime();
        try {
            priceCollectorService.getDayAheadPricePoints(startDate,endDate);
        } catch (URISyntaxException e) {
            logger.error("Could net fetch data",e);
        } catch (ParseException e) {
            logger.error("Could net fetch data",e);
        }
    }
}
