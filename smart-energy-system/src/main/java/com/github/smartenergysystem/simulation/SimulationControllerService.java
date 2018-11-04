package com.github.smartenergysystem.simulation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SimulationControllerService implements ISimulationControllerService{
	
	//Replace with a database after we decided which database we use
	//probably no need for atomic types because of synchronized methods
	Long photovoltaicPanelsId = 0L;
	HashMap<Long,PhotovoltaicPanel> photovoltaicPanels = new HashMap<>();
	
	Long windTurbineId = 0L;
	HashMap<Long,WindTurbine> windPanels = new HashMap<>();
	
	@Override
	public synchronized Long addPhotovoltaicPanel(PhotovoltaicPanel photovoltaicPanel) {
		photovoltaicPanelsId++;
		photovoltaicPanels.put(photovoltaicPanelsId,photovoltaicPanel);
		return photovoltaicPanelsId;
	}
	
	public synchronized Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels() {
		return photovoltaicPanels;
	}

	@Override
	public PhotovoltaicPanel getPhotovoltaicPanel(Long panelId) {
		return photovoltaicPanels.get(panelId);
	}

	@Override
	public Long addWindTurbine(WindTurbine windTurbine) {
		windTurbineId++;
		getWindTurbines().put(windTurbineId,windTurbine);
		return windTurbineId;
	}

	@Override
	public Map<Long, WindTurbine> getWindTurbines() {
		return windPanels;
	}

	@Override
	public WindTurbine getWindTurbine(Long turbineId) {
		return windPanels.get(turbineId);
	}

	@Override
	public double computeEnergyGeneratedPhotovoltaicPanel(Long panelId) {
		PhotovoltaicPanel photovoltaicPanel = photovoltaicPanels.get(panelId);
		//Get From DB
		//photovoltaicPanel.computeEnergyGenerated(temperatureInCelsius, sunpowerHorizontal, dayOfYear)
		return 0;
	}

	@Override
	public double computeEnergyGeneratedWindTurbine(Long panelId) {
		return 0;
	}
	
	


}
