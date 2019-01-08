package com.github.smartenergysystem.price.collector.priceCollector;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class PriceCollectorController {

    private static final String DATE_PATTERN = "yyyy-MM-dd-HH";

    @Value("entsoe.securityToken")
    private String securityToken;

    @Value("entsoe.area")
    private String area;


    @ApiOperation(value = "Inputs are always UTC")
    @GetMapping("/prices")
    public void getDayAheadPrices(@RequestParam("from") @DateTimeFormat(pattern= DATE_PATTERN) Date startDate,
                                             @RequestParam("from") @DateTimeFormat(pattern=DATE_PATTERN) Date endDate) {
        PriceCollector priceCollector = new PriceCollector(securityToken);
        //priceCollector.collectPrices(PriceCollector.DocumentTypes.DAY_AHEAD_PRICES,area,startDate,endDate);
    }
}
