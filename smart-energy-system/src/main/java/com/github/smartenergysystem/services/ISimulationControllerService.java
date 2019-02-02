package com.github.smartenergysystem.services;

import com.github.smartenergysystem.simulation.SmartGridSolverSolution;

public interface ISimulationControllerService {
	//public Long addPhotovoltaicPanel(PhotovoltaicPanel photovoltaicPanel);
	//public Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels();
	//public PhotovoltaicPanel getPhotovoltaicPanel(Long panelId);
	//public double computeEnergyGeneratedPhotovoltaicPanel(Long panelId);
	//public EnergyForecast computeEnergyGenerateForecastPhotovoltaicPanel(Long panelId, long maxTimestampOffset);

	//public Long addWindTurbine(WindTurbine windTurbine);
	//public Map<Long,WindTurbine> getWindTurbines();
	//public WindTurbine getWindTurbine(Long turbineId);
	//public double computeEnergyGeneratedWindTurbine(Long id);
	//public EnergyForecast computeEnergyGenerateForecastWindTurbine(Long id, long maxTimestampOffset);

	//public Long addBattery(Battery battery);
	//public Battery getBattery(Long id);
	//public Map<Long,Battery> getBatterys();
	//public Battery computebatteryChargeProcess(long id, ChargeProcessInput chargeProcessInput);

	//public Long addOfficeBuilding(OfficeBuilding officeBuilding);
	//public OfficeBuilding getOfficeBuilding(Long id);
	//public Map<Long,OfficeBuilding> getOfficeBuildings();
	//public double computeOfficeBuildingDemand(long id, int hourOfTheDay);
	//public void setOfficeBuildingsHourlyBaseDemandPerSquareMeter(long id,double[] hourlyBaseDemandPerSquareMeter);

	//public Long addHomeBuilding(Home home);
	//public Home getHomeBuilding(long id);
	//public Map<Long, Home> getHomeBuildings();
	//public double computeHomeBuildingDemand(long id, int hourOfTheDay);
	//public void setHomeBuildingsHourlyBaseDemandPerSquareMeter(long id, double[] hourlyBaseDemandPerSquareMeter);
	public SmartGridSolverSolution solve(int calculationBound, int exportPrice, int efficiencyChargingAsPartsOfHundred);

	//public void deleteHome(long id);

	//public void deleteOfficeBuilding(long id);

	//public void deletePhotovoltaicPanel(long id);

	//public void deleteWindTurbine(long id);

	//public void deleteBatterie(long id);
}
