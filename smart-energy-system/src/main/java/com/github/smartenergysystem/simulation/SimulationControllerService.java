package com.github.smartenergysystem.simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SimulationControllerService implements ISimulationControllerService{
	
	//Replace with a database after we decided which database we use
	//probably no need for atomic types because of synchronized methods
	Long id = 0L;
	HashMap<Long,PhotovoltaicPanel> photovoltaicPanels = new HashMap<>();
	
	@Override
	public synchronized Long addPhotovoltaicPanel(PhotovoltaicPanel photovoltaicPanel) {
		id++;
		photovoltaicPanels.put(id,photovoltaicPanel);
		return id;
	}
	
	public synchronized Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels() {
		return photovoltaicPanels;
	}


}
