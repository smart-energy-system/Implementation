package com.github.smartenergysystem.services;

import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.model.EnergyForecastPoint;
import com.github.smartenergysystem.model.exeptions.IdNotFoundException;
import com.github.smartenergysystem.simulation.WindTurbine;
import com.github.smartenergysystem.weather.WeatherForecast;
import com.github.smartenergysystem.weather.WeatherHistory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WindTurbineService extends EntityService {

    Long windTurbineId = 0L;
    HashMap<Long, WindTurbine> windTurbines = new HashMap<>();

    public synchronized Long addWindTurbine(WindTurbine windTurbine) {
        try {
            registerForWeatherData(windTurbine.getLatitude(), windTurbine.getLongitude());
        } catch (Exception e) {
            //TO not catch every exception
            logger.error("Could not use weather collector", e);
        }
        windTurbineId++;
        getWindTurbines().put(windTurbineId, windTurbine);
        return windTurbineId;
    }


    public synchronized Map<Long, WindTurbine> getWindTurbines() {
        return windTurbines;
    }

    public synchronized WindTurbine getWindTurbine(Long turbineId) {
        if (windTurbines.containsKey(turbineId)) {
            return windTurbines.get(turbineId);
        } else {
            throw new IdNotFoundException();
        }
    }


    public synchronized double computeEnergyGeneratedWindTurbine(Long id) {
        WeatherHistory weatherHistory = getMostRecentWeatherHistoryForSupplier(id, windTurbines);
        WindTurbine windTurbine = windTurbines.get(id);
        double energy = windTurbine.computeEnergyGenerated(weatherHistory.getWindSpeed(),
                weatherHistory.getAirPressureInPascal(), weatherHistory.getHumidity(), weatherHistory.getTemperature());
        return energy;
    }


    /**
     * Solves A2.5
     */
    public synchronized EnergyForecast computeEnergyGenerateForecastWindTurbine(Long id, long maxTimestampOffset) {
        List<WeatherForecast> weatherForecastList = getWeatherForecastForSupplier(id, maxTimestampOffset, windTurbines);
        logger.debug("Got " + weatherForecastList.size() + " forecasts");
        WindTurbine windTurbine = windTurbines.get(id);
//        TreeMap<Long, Double> energyforecastMap = new TreeMap<>();
//        weatherForecastList.forEach(weatherforecast -> {
//            logger.debug("Weatherforecast " + weatherforecast.toString());
//            double energy = windTurbine.computeEnergyGenerated(weatherforecast.getWindSpeed(),
//                    weatherforecast.getAirPressureInPascal(), weatherforecast.getHumidity(),
//                    weatherforecast.getTemperature());
//            logger.debug("Energy for this forecast:" + energy);
//            energyforecastMap.put(weatherforecast.getTimestamp(), energy);
//        });
//        EnergyForecast energyForecast = new EnergyForecast();
//        logger.debug("Size of generation forecast:" + energyforecastMap.size());
//        energyForecast.setForecast(energyforecastMap);
        List<EnergyForecastPoint> forecast = new LinkedList<>();
        weatherForecastList.forEach(weatherforecast -> {
            double energy = windTurbine.computeEnergyGenerated(weatherforecast.getWindSpeed(),
                    weatherforecast.getAirPressureInPascal(), weatherforecast.getHumidity(),
                    weatherforecast.getTemperature());
            forecast.add(new EnergyForecastPoint(weatherforecast.getTimestamp(),energy));
        });
        EnergyForecast energyForecast = new EnergyForecast();
        logger.debug("Size of generation forecast:" + forecast.size());
        energyForecast.setForecast(forecast);
        return energyForecast;
    }

    public void deleteWindTurbine(long id) {
        windTurbines.remove(id);
    }

    public void putWindTurbine(long id, WindTurbine windTurbine) {
        windTurbines.put(id,windTurbine);
    }
}
