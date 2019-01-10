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

    @Autowired
    PriceCollectorService priceCollectorService;

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
                List<DayAheadPricePoint> replyList = priceCollectorService.getDayAheadPricePoints(startDate, endDate);
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

}
