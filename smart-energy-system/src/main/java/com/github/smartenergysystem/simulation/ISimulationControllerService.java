package com.github.smartenergysystem.simulation;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.github.smartenergysystem.model.ChargeProcessInput;
import com.github.smartenergysystem.model.EnergyForecast;

public interface ISimulationControllerService {
	public Long addPhotovoltaicPanel(PhotovoltaicPanel photovoltaicPanel);
	public Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels();
	public PhotovoltaicPanel getPhotovoltaicPanel(Long panelId);
	public double computeEnergyGeneratedPhotovoltaicPanel(Long panelId);
	public EnergyForecast computeEnergyGenerateForecastPhotovoltaicPanel(Long panelId, long maxTimestampOffset);
	
	public Long addWindTurbine(WindTurbine windTurbine);
	public Map<Long,WindTurbine> getWindTurbines();
	public WindTurbine getWindTurbine(Long turbineId);
	public double computeEnergyGeneratedWindTurbine(Long id);
	public EnergyForecast computeEnergyGenerateForecastWindTurbine(Long id, long maxTimestampOffset);
	
	public Battery addBattery(Battery battery);
	public Battery getBattery(Long id);
	public Collection<Battery> getBatterys();
	public Battery computebatteryChargeProcess(long id, ChargeProcessInput chargeProcessInput);
}
