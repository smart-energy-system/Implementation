package com.github.smartenergysystem.services;

import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.simulation.PhotovoltaicPanel;
import com.github.smartenergysystem.weather.WeatherForecast;
import com.github.smartenergysystem.weather.WeatherHistory;
import com.github.smartenergysystem.weather.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhotovoltaicPanelsService extends EntityService {

    Logger logger = LoggerFactory.getLogger(PhotovoltaicPanelsService.class);

    Long photovoltaicPanelsId = 0L;
    HashMap<Long, PhotovoltaicPanel> photovoltaicPanels = new HashMap<>();

    public synchronized Long addPhotovoltaicPanel(PhotovoltaicPanel photovoltaicPanel) {
        try{
            WeatherResponse weatherResponse = registerForWeatherData(photovoltaicPanel.getLatitude(),
                    photovoltaicPanel.getLongitude());
        }catch(Exception e){
            //TO not catch every exception
            logger.error("Could not use weather collector",e);
        }
        photovoltaicPanelsId++;
        photovoltaicPanels.put(photovoltaicPanelsId, photovoltaicPanel);
        return photovoltaicPanelsId;
    }

    public synchronized Map<Long, PhotovoltaicPanel> getPhotovoltaicPanels() {
        return photovoltaicPanels;
    }

    public synchronized PhotovoltaicPanel getPhotovoltaicPanel(Long panelId) {
        return photovoltaicPanels.get(panelId);
    }

    public synchronized double computeEnergyGeneratedPhotovoltaicPanel(Long panelId) {
        WeatherHistory weatherHistory = getMostRecentWeatherHistoryForSupplier(panelId, photovoltaicPanels);
        PhotovoltaicPanel photovoltaicPanel = photovoltaicPanels.get(panelId);
        int dayOfTheYear = getDayOfTheYear(weatherHistory.getTimestamp());
        logger.debug("Calculating photovoltaic panel energy for temp:" + weatherHistory.getTemperature() + " sunpower:"
                + weatherHistory.getGlobalHorizontalSolarIrradiance() + " dayOfTheYear:" + dayOfTheYear);
        double energy = photovoltaicPanel.computeEnergyGenerated(weatherHistory.getTemperature(),
                weatherHistory.getGlobalHorizontalSolarIrradiance(), dayOfTheYear);
        logger.debug("Result:" + energy);
        return energy;

    }

    public void deletePhotovoltaicPanel(long id) {
        photovoltaicPanels.remove(id);
    }

    private synchronized int getDayOfTheYear(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        int dayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);
        return dayOfTheYear;
    }

    /**
     * Solves A2.9
     */
    public synchronized EnergyForecast computeEnergyGenerateForecastPhotovoltaicPanel(Long id,
                                                                                      long maxTimestampOffset) {
        List<WeatherForecast> weatherForecastList = getWeatherForecastForSupplier(id, maxTimestampOffset,
                photovoltaicPanels);
        PhotovoltaicPanel photovoltaicPanel = photovoltaicPanels.get(id);
        TreeMap<Long, Double> energyforecastMap = new TreeMap<>();
        weatherForecastList.forEach(weatherforecast -> {
            double energy = photovoltaicPanel.computeEnergyGenerated(weatherforecast.getTemperature(),
                    weatherforecast.getGlobalHorizontalSolarIrradiance(),
                    getDayOfTheYear(weatherforecast.getTimestamp()));
            energyforecastMap.put(weatherforecast.getTimestamp(), energy);
        });
        EnergyForecast energyForecast = new EnergyForecast();
        logger.debug("Size of generation forecast:" + energyforecastMap.size());
        energyForecast.setForecast(energyforecastMap);
        return energyForecast;

    }
}
