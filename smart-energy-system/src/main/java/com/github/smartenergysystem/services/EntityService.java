package com.github.smartenergysystem.services;

import com.github.smartenergysystem.model.exeptions.IdNotFoundException;
import com.github.smartenergysystem.model.exeptions.NoWeatherDataFoundException;
import com.github.smartenergysystem.simulation.Entity;
import com.github.smartenergysystem.weather.*;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class EntityService {

    Logger logger = LoggerFactory.getLogger(EntityService.class);

    @Autowired
    WeatherRepository weatherForecastRepository;
    @Autowired
    WeatherCompleteRepository weatherCompleteRepository;


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

    protected synchronized List<WeatherItem> getWeatherForecastForSupplier(Long id, Date startDate, Date endDate,
                                                                             HashMap<Long, ? extends Entity> entityList) {
        if (entityList.containsKey(id)) {
            Entity entity = entityList.get(id);

            WeatherItem closestWeatherItem =  findClosestPosition(entity);

            //Get weather data for this location
            List<WeatherItem> weatherItems = weatherCompleteRepository.findByLatitudeAndLongitudeOrderByTimestampDesc(closestWeatherItem.getLatitude(),closestWeatherItem.getLongitude());
            Calendar calender = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            weatherItems.forEach(weatherItem -> {
                calender.setTime(roundDownToNearestHour(new Date(weatherItem.getTimestamp())));
                long roundedTimestamp = calender.getTimeInMillis();
                weatherItem.setTimestamp(roundedTimestamp);
            });

            List<WeatherItem> weatherItemsInRightTime = getWeatherItemsInRightTime(startDate, endDate, weatherItems);

            List<Date> requieredDates = getRequiredDates(startDate,endDate);
            logger.info("There are " + requieredDates.size() + " Hours requiered");
            List<Date> missingHours = calculateMissingHours(requieredDates,weatherItemsInRightTime);
            missingHours.forEach(date -> logger.info("Missing:"+date));
            missingHours.forEach(date -> weatherItemsInRightTime.add(extrapolateMissingHour(date,weatherItems,closestWeatherItem.getLatitude(),closestWeatherItem.getLongitude())));
            logger.info("There are " + weatherItemsInRightTime.size() + " Hours found");
            weatherItemsInRightTime.forEach(weatherItem ->logger.info("Got:"+ weatherItem));
            Collections.sort(weatherItemsInRightTime, new Comparator<WeatherItem>() {
                @Override
                public int compare(WeatherItem o1, WeatherItem o2) {
                    Date d1 = new Date(o1.getTimestamp());
                    Date d2 = new Date(o2.getTimestamp());
                    return d1.compareTo(d2);
                }
            });
            return weatherItemsInRightTime;
//            logger.debug("Requesting forecast data for entity " + id);
//            List<WeatherForecast> weatherForecastList = weatherForecastRepository
//                    .findByLatitudeAndLongitudeAndTimestampLessThan(entity.getLatitude(), entity.getLongitude(),
//                            System.currentTimeMillis() + maxTimestampOffset);
//            if (weatherForecastList != null && weatherForecastList.size() > 0) {
//                return weatherForecastList;
//            } else {
//                logger.debug("Got none forecasts");
//                throw new NoWeatherDataFoundException();
//            }
        } else {
            throw new IdNotFoundException();
        }
    }

    private WeatherItem extrapolateMissingHour(Date date,List<WeatherItem> weatherItems, double lat, double lon){
        logger.info("Searching for Date:"+date);
        Calendar calender = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar calenderCompare = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calenderCompare.setTime(date);
        for(WeatherItem weatherItem : weatherItems) {
            calender.setTime(new Date(weatherItem.getTimestamp()));
            if (calender.get(Calendar.HOUR_OF_DAY) == calenderCompare.get(Calendar.HOUR_OF_DAY)) {
                WeatherItem weatherItemReturn = new WeatherItem();
                weatherItemReturn.setTimestamp(calenderCompare.getTimeInMillis());
                weatherItemReturn.setAirPressure(weatherItem.getAirPressure());
                weatherItemReturn.setGlobalHorizontalSolarIrradiance(weatherItem.getGlobalHorizontalSolarIrradiance());
                weatherItemReturn.setHumidity(weatherItem.getHumidity());
                weatherItemReturn.setTemperature(weatherItem.getTemperature());
                weatherItemReturn.setWindSpeed(weatherItem.getWindSpeed());
                weatherItemReturn.setLatitude(weatherItem.getLatitude());
                weatherItemReturn.setLongitude(weatherItem.getLongitude());
                logger.info("Found old value in db from:"+ new Date(weatherItem.getTimestamp()) + " new Date is: " + new Date(weatherItemReturn.getTimestamp()) );
                return weatherItemReturn;
            }
/*        weatherItems.forEach(weatherItem -> {
            calender.setTime(new Date(weatherItem.getTimestamp()));
            if(calender.get(Calendar.HOUR_OF_DAY) == calenderCompare.get(Calendar.HOUR_OF_DAY)){
                WeatherItem weatherItemReturn = new WeatherItem();
                weatherItemReturn.setTimestamp(calenderCompare.getTimeInMillis());
                weatherItemReturn.setAirPressure(weatherItem.getAirPressure());
                weatherItemReturn.setGlobalHorizontalSolarIrradiance(weatherItem.getGlobalHorizontalSolarIrradiance());
                weatherItemReturn.setHumidity(weatherItem.getHumidity());
                weatherItemReturn.setTemperature(weatherItem.getTemperature());
                weatherItemReturn.setWindSpeed(weatherItem.getWindSpeed());
                weatherItemReturn.setLatitude(weatherItem.getLatitude());
                weatherItemReturn.setLongitude(weatherItem.getLongitude());
                return weatherItemReturn;
            }
        });*/
        }
        WeatherItem weatherItemReturn = new WeatherItem();
        weatherItemReturn.setTimestamp(calenderCompare.getTimeInMillis());
        weatherItemReturn.setAirPressure(1002.0);
        weatherItemReturn.setGlobalHorizontalSolarIrradiance(150);
        weatherItemReturn.setHumidity(0.82);
        weatherItemReturn.setTemperature(5);
        weatherItemReturn.setWindSpeed(3.6);
        weatherItemReturn.setLatitude(lat);
        weatherItemReturn.setLongitude(lon);
        return weatherItemReturn;
    }

    private List<Date> calculateMissingHours(List<Date> requiredHours, List<WeatherItem> weatherItems) {
        List<Date> missingHours = new LinkedList<>(requiredHours);
        //Is not the fastest solution because we iterate over weatherItems for each element of missingHours
        Predicate<Date> isContaintInWeatherItemList = date -> weatherItems.stream().map(weatherItem -> roundDownToNearestHour(new Date(weatherItem.getTimestamp()))).anyMatch(weatherDate -> weatherDate.equals(date));
        missingHours.removeIf(isContaintInWeatherItemList);
        return missingHours;
    }

    /**
     * Calculates a list of hours which must be in the answer
     * See price collector
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Date> getRequiredDates(Date startDate, Date endDate) {
        Date startDateHour = roundDownToNearestHour(startDate);
        Date endDateHour  = roundDownToNearestHour(endDate);
        Calendar calender = getUtcCalendar(startDateHour);
        Date tempDate = calender.getTime();
        List<Date> requiredHours = new LinkedList<>();
        requiredHours.add(startDateHour);
        while (tempDate.before(endDateHour)) {
            calender.add(Calendar.HOUR, 1);
            tempDate = calender.getTime();
            requiredHours.add(tempDate);
        }
        return requiredHours;
    }

    public static Date roundDownToNearestHour(Date date){
        Calendar c = getUtcCalendar(date);
        c.set(Calendar.MILLISECOND,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        return c.getTime();
    }

    private static Calendar getUtcCalendar(Date startDate) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeZone(TimeZone.getTimeZone("UTC"));
        calender.setTime(startDate);
        return calender;
    }

    private List<WeatherItem>  getWeatherItemsInRightTime(Date startDate, Date endDate, List<WeatherItem> weatherItems) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(startDate);
        long startTimestamp = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long endTimestamp = calendar.getTimeInMillis();
        List<WeatherItem> weatherItemsInRightTime = new LinkedList<>();
        weatherItems.forEach(weatherItem -> {
            if(weatherItem.getTimestamp()>= startTimestamp && weatherItem.getTimestamp()<=endTimestamp){
                weatherItemsInRightTime.add(weatherItem);
            }
        });
        logger.info("There are "+ weatherItemsInRightTime.size() + " weather items in the right time");
        return weatherItemsInRightTime;
    }

    private WeatherItem findClosestPosition(Entity entity) {
        List<WeatherItem> weatherTotal = weatherCompleteRepository.findAll();
        List<LatLng> positionList = new LinkedList<>();
        //Find closes
        LatLng entityPos = new LatLng(entity.getLatitude(),entity.getLongitude());
        double closestDistanceInMeter = Double.MAX_VALUE;// = LatLngTool.distance()
        WeatherItem closest = null;
        for(WeatherItem pos : weatherTotal){
            LatLng tempPos = new LatLng(pos.getLatitude(),pos.getLongitude());
            double distance = LatLngTool.distance(entityPos, tempPos, LengthUnit.METER);
            if (distance<closestDistanceInMeter) {
                closest = pos;
                closestDistanceInMeter = distance;
            }
        }
        logger.info("Found the closest pos:" +  closest.getLatitude() + ":"+ closest.getLongitude()+ " to:" +  entity.getLatitude() + ":"+ entity.getLongitude() + " with" +
                "distance " + closestDistanceInMeter+  " in m" );
        return closest;
    }
    protected synchronized List<WeatherForecast> getWeatherForecastForSupplier(Long id, long maxTimestampOffset,
                                                                              HashMap<Long, ? extends Entity> entityList) {
                                                                               return null;}

//    protected synchronized List<WeatherForecast> getWeatherForecastForSupplier(Long id, long maxTimestampOffset,
//                                                                               HashMap<Long, ? extends Entity> entityList) {
//        if (entityList.containsKey(id)) {
//            Entity entity = entityList.get(id);
//
//            List<WeatherCollector>  weatherCollectors = weatherCollectorRepository.findAll();
//
//            //Find closes
//            LatLng entityPos = new LatLng(entity.getLatitude(),entity.getLongitude());
//            double closestDistanceInMeter = Double.MAX_VALUE;// = LatLngTool.distance()
//            WeatherCollector closest = null;
//            for(WeatherCollector weatherCollector : weatherCollectors){
//                LatLng tempPos = new LatLng(weatherCollector.getLatitude(),weatherCollector.getLongitude());
//                double distance = LatLngTool.distance(entityPos, tempPos, LengthUnit.METER);
//                if (distance<closestDistanceInMeter) {
//                    closest = weatherCollector;
//                }
//            }
//
//
//
//            logger.debug("Requesting forecast data for entity " + id);
//            List<WeatherForecast> weatherForecastList = weatherForecastRepository
//                    .findByLatitudeAndLongitudeAndTimestampLessThan(entity.getLatitude(), entity.getLongitude(),
//                            System.currentTimeMillis() + maxTimestampOffset);
//            if (weatherForecastList != null && weatherForecastList.size() > 0) {
//                return weatherForecastList;
//            } else {
//                logger.debug("Got none forecasts");
//                throw new NoWeatherDataFoundException();
//            }
//        } else {
//            throw new IdNotFoundException();
//        }
//    }

    protected boolean isIdValid(Long id, Map<Long, ?> entities) {
        if (entities.containsKey(id)) {
            return true;
        } else {
            throw new IdNotFoundException();
        }
    }
}
