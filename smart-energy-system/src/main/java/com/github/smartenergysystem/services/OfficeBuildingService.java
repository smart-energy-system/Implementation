package com.github.smartenergysystem.services;

import com.github.smartenergysystem.simulation.OfficeBuilding;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OfficeBuildingService extends EntityService {

    Long officeBuildingId = 0L;
    HashMap<Long, OfficeBuilding> officeBuildings = new HashMap<>();

    public synchronized Long addOfficeBuilding(OfficeBuilding officeBuilding) {
        officeBuildingId++;
        officeBuildings.put(officeBuildingId, officeBuilding);
        return officeBuildingId;
    }

    public void deleteOfficeBuilding(long id) {
        officeBuildings.remove(id);
    }

    public synchronized OfficeBuilding getOfficeBuilding(Long id) {
        isIdValid(id, officeBuildings);
        return officeBuildings.get(id);
    }

    public synchronized Map<Long, OfficeBuilding> getOfficeBuildings() {
        return officeBuildings;
    }

    public synchronized double computeOfficeBuildingDemand(long id, int hourOfTheDay) {
        isIdValid(id, officeBuildings);
        return officeBuildings.get(id).calculateDemand(hourOfTheDay);
    }

    public synchronized void setOfficeBuildingsHourlyBaseDemandPerSquareMeter(long id, double[] hourlyBaseDemandPerSquareMeter) {
        isIdValid(id, officeBuildings);
        officeBuildings.get(id).setHourlyBaseDemandPerSquareMeter(hourlyBaseDemandPerSquareMeter);
    }
}
