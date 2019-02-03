package com.github.smartenergysystem.services;

import com.github.smartenergysystem.model.ChargeProcessInput;
import com.github.smartenergysystem.simulation.Battery;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class BatteryService  extends EntityService {

    Long batteryId = 0L;
    HashMap<Long, Battery> batteries = new HashMap<>();

    public synchronized Long addBattery(Battery battery) {
        batteryId++;
        batteries.put(batteryId, battery);
        return batteryId;
    }

    public synchronized Battery getBattery(Long id) {
        isIdValid(id, batteries);
        return batteries.get(id);
    }

    public synchronized Map<Long, Battery> getBatterys() {
        return batteries;
    }

    public synchronized Battery computebatteryChargeProcess(long id, ChargeProcessInput chargeProcessInput) {
        isIdValid(id, batteries);
        Battery battery = batteries.get(id);
        battery.computeStoredEnergy(chargeProcessInput.getDischargingRate(), chargeProcessInput.getChargingRate(),
                chargeProcessInput.getChargingEfficiency());
        return battery;
    }

    public void deleteBatterie(long id) {
        batteries.remove(id);
    }

    public void putBattery(long id, Battery battery) {
        isIdValid(id, batteries);
        batteries.put(id,battery);
    }
}
