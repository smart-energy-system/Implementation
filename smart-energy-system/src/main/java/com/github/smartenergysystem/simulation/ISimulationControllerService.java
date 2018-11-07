package com.github.smartenergysystem.simulation;

import java.util.Map;

import com.github.smartenergysystem.model.EnergyForecast;

public interface ISimulationControllerService {
	public Long addPhotovoltaicPanel(PhotovoltaicPanel photovoltaicPanel);
	public Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels();
	public PhotovoltaicPanel getPhotovoltaicPanel(Long panelId);
	public double computeEnergyGeneratedPhotovoltaicPanel(Long panelId);
	public EnergyForecast computeEnergyGenerateForecastPhotovoltaicPanel(Long panelId);
	public boolean existsPhotovoltaicPanel(Long panelId);
	
	public Long addWindTurbine(WindTurbine windTurbine);
	public Map<Long,WindTurbine> getWindTurbines();
	public WindTurbine getWindTurbine(Long turbineId);
	public double computeEnergyGeneratedWindTurbine(Long id);
	public EnergyForecast computeEnergyGenerateForecastWindTurbine(Long turbineId);
	public boolean existsWindTurbine(Long turbineId);
}
