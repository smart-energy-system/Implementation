package com.github.smartenergysystem.services;

import com.github.smartenergysystem.model.exeptions.IdNotFoundException;
import com.github.smartenergysystem.model.exeptions.NoWeatherDataFoundException;
import com.github.smartenergysystem.simulation.Entity;
import com.github.smartenergysystem.weather.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityService {

    Logger logger = LoggerFactory.getLogger(EntityService.class);

    @Autowired
    WeatherRepository weatherForecastRepository;

    @Value("${weatherService.url}")
    String weatherServiceUrl;

    protected synchronized WeatherResponse registerForWeatherData(double latitude, double longitude) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        RestTemplate restTemplate = new RestTemplate();
        WeatherRequest weatherRequest = new WeatherRequest(latitude, longitude);
        HttpEntity<WeatherRequest> httpRequest = new HttpEntity<WeatherRequest>(weatherRequest, httpHeaders);
        ResponseEntity<WeatherResponse> weatherResponseEntity = restTemplate.postForEntity(weatherServiceUrl,
                httpRequest, WeatherResponse.class);
        return weatherResponseEntity.getBody();
    }

    protected synchronized WeatherHistory getMostRecentWeatherHistoryForSupplier(Long id,
                                                                               HashMap<Long, ? extends Entity> entityList) {
        if (entityList.containsKey(id)) {
            Entity entity = entityList.get(id);
            logger.debug("Requesting most recent weather data for entity " + id);
            WeatherHistory weatherHistory = weatherForecastRepository
                    .findFirstByLatitudeAndLongitudeOrderByTimestampDesc(entity.getLatitude(), entity.getLongitude());
            if (weatherHistory != null) {
                logger.debug("Most recent data is from:" + weatherHistory.getTimestamp());
                return weatherHistory;
            } else {
                throw new NoWeatherDataFoundException();
            }
        } else {
            throw new IdNotFoundException();
        }
    }

    protected synchronized List<WeatherForecast> getWeatherForecastForSupplier(Long id, long maxTimestampOffset,
                                                                             HashMap<Long, ? extends Entity> entityList) {
        if (entityList.containsKey(id)) {
            Entity entity = entityList.get(id);
            logger.debug("Requesting forecast data for entity " + id);
            List<WeatherForecast> weatherForecastList = weatherForecastRepository
                    .findByLatitudeAndLongitudeAndTimestampLessThan(entity.getLatitude(), entity.getLongitude(),
                            System.currentTimeMillis() + maxTimestampOffset);
            if (weatherForecastList != null && weatherForecastList.size() > 0) {
                return weatherForecastList;
            } else {
                logger.debug("Got none forecasts");
                throw new NoWeatherDataFoundException();
            }
        } else {
            throw new IdNotFoundException();
        }
    }

    protected boolean isIdValid(Long id, Map<Long, ?> entities) {
        if (entities.containsKey(id)) {
            return true;
        } else {
            throw new IdNotFoundException();
        }
    }
}
