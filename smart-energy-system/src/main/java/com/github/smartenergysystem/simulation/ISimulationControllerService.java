package com.github.smartenergysystem.simulation;

import java.util.Map;

public interface ISimulationControllerService {
	public Long addPhotovoltaicPanel(PhotovoltaicPanel photovoltaicPanel);
	public Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels();
	public PhotovoltaicPanel getPhotovoltaicPanel(Long panelId);
	public double computeEnergyGeneratedPhotovoltaicPanel(Long panelId);
	
	public Long addWindTurbine(WindTurbine windTurbine);
	public Map<Long,WindTurbine> getWindTurbines();
	public WindTurbine getWindTurbine(Long turbineId);
	public double computeEnergyGeneratedWindTurbine(Long panelId);
}
