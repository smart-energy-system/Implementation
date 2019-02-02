package com.github.smartenergysystem.services;

import com.github.smartenergysystem.simulation.Home;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomesService extends EntityService {

    Long homeBuildingId = 0L;
    HashMap<Long, Home> homeBuildings = new HashMap<>();

    public synchronized Long addHomeBuilding(Home home) {
        homeBuildingId++;
        homeBuildings.put(homeBuildingId, home);
        return homeBuildingId;
    }

    public synchronized void deleteHome(long id) {
        homeBuildings.remove(id);
    }


    public synchronized Home getHomeBuilding(long id) {
        isIdValid(id, homeBuildings);
        return homeBuildings.get(id);
    }

    public synchronized Map<Long, Home> getHomeBuildings() {
        // TODO Auto-generated method stub
        return homeBuildings;
    }

    public synchronized double computeHomeBuildingDemand(long id, int hourOfTheDay) {
        isIdValid(id, homeBuildings);
        return homeBuildings.get(id).calculateDemand(hourOfTheDay);
    }

    public synchronized void setHomeBuildingsHourlyBaseDemandPerSquareMeter(long id, double[] hourlyBaseDemandPerSquareMeter) {
        isIdValid(id, homeBuildings);
        homeBuildings.get(id).setHourlyBaseDemandPerSquareMeter(hourlyBaseDemandPerSquareMeter);
    }
}
