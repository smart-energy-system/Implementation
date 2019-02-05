package com.github.smartenergysystem.services;

import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.model.EnergyForecastPoint;
import com.github.smartenergysystem.simulation.Consumer;
import com.github.smartenergysystem.simulation.Home;

import java.util.*;

public class ConsumerService extends EntityService{

    public synchronized EnergyForecast getDemandForecast(Consumer consumer, long offset){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE,0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MILLISECOND,(int) offset);
        Date endDate = calendar.getTime();
        List<EnergyForecastPoint> energyForecastPoints = new LinkedList<>();
        Date timeStep = startDate;
        while(timeStep.before(endDate)){
            Calendar tempCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            tempCalendar.setTime(timeStep);
            energyForecastPoints.add(new EnergyForecastPoint(tempCalendar.getTimeInMillis(),consumer.calculateDemand(tempCalendar.get(Calendar.HOUR_OF_DAY))));
            tempCalendar.add(Calendar.HOUR_OF_DAY,1);
            timeStep = tempCalendar.getTime();
        }
        EnergyForecast energyForecast = new EnergyForecast();
        energyForecast.setUnit("W");
        energyForecast.setForecast(energyForecastPoints);
        return energyForecast;
    }
}
