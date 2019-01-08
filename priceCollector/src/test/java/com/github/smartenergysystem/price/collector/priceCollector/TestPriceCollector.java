package com.github.smartenergysystem.price.collector.priceCollector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPriceCollector {

    @Value("${entsoe.securityToken}")
    private String securityToken;

    @Test
    public void testCollector() throws Exception {
        PriceCollector priceCollector = new PriceCollector(securityToken);
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE,1);
        Date now1 = c.getTime();
        List<DayAheadPricePoint> priceList = priceCollector.collectPrices(PriceCollector.DocumentTypes.DAY_AHEAD_PRICES,"10Y1001A1001A82H",now,now1);
        priceList.forEach(System.out::println);
    }
}
