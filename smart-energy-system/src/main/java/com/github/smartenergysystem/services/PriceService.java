package com.github.smartenergysystem.services;

import com.github.smartenergysystem.model.exeptions.PriceServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class PriceService {

    private static final int CONVERT_MWH_TO_KWH = 1000;
    private static final int CONVERT_EURO_TO_CENT = 100;

    Logger logger = LoggerFactory.getLogger(PriceService.class);
    @Value("${priceCollector.url}")
    String priceCollectorUrl;

    public synchronized int[] requestPrices(int numberOfHours){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE,0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY,numberOfHours);
        Date endDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(priceCollectorUrl)
                .queryParam("startDate",dateFormat.format(startDate))
                .queryParam("endDate",dateFormat.format(endDate));
        logger.info("Price request uri:"+builder.toUriString());
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<DayAheadPricePoint>> parameterizedTypeReferencePriceList  = new ParameterizedTypeReference<List<DayAheadPricePoint>>(){};
        ResponseEntity<List<DayAheadPricePoint>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, parameterizedTypeReferencePriceList);
        if(response.getStatusCode().value() == HttpStatus.OK.value()){
            logger.info("Got following prices");
            List<DayAheadPricePoint> pricePointList = response.getBody();
            pricePointList.forEach(price -> logger.info(price.toString()));
            int[] prices = new int[pricePointList.size()];
            int i = 0;
            for (DayAheadPricePoint dayAheadPricePoint : pricePointList) {
                prices[i] = (int)((dayAheadPricePoint.getPriceInEuroPerMWh()/ CONVERT_MWH_TO_KWH) * CONVERT_EURO_TO_CENT);
                logger.info("Got Cent price per kWh:"+prices[i]);
                i++;
            }
            return prices;
        }else{
            throw new PriceServiceException();
        }
    }
}
