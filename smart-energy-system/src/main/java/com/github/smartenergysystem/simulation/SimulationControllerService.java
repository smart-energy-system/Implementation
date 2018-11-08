package com.github.smartenergysystem.simulation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.smartenergysystem.model.ChargeProcessInput;
import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.model.exeptions.IdNotFoundException;
import com.github.smartenergysystem.model.exeptions.NoWeatherDataFoundException;
import com.github.smartenergysystem.weather.WeatherForecast;
import com.github.smartenergysystem.weather.WeatherHistory;
import com.github.smartenergysystem.weather.WeatherRepository;
import com.github.smartenergysystem.weather.WeatherRequest;
import com.github.smartenergysystem.weather.WeatherResponse;

@Service
public class SimulationControllerService implements ISimulationControllerService {

	Logger logger = LoggerFactory.getLogger(SimulationControllerService.class);

	@Autowired
	WeatherRepository weatherForecastRepository;

	@Value("${weatherService.url}")
	String weatherServiceUrl;

	// Replace with a database after we decided which database we use
	// probably no need for atomic types because of synchronized methods
	Long photovoltaicPanelsId = 0L;
	HashMap<Long, PhotovoltaicPanel> photovoltaicPanels = new HashMap<>();

	Long windTurbineId = 0L;
	HashMap<Long, WindTurbine> windTurbines = new HashMap<>();

	Long batteryId = 0L;
	HashMap<Long, Battery> batteries = new HashMap<>();

	Long officeBuildingId = 0L;
	HashMap<Long, OfficeBuilding> officeBuildings = new HashMap<>();
	
	Long homeBuildingId = 0L;
	HashMap<Long, Home> homeBuildings = new HashMap<>();

	@Override
	public synchronized Long addPhotovoltaicPanel(PhotovoltaicPanel photovoltaicPanel) {
		WeatherResponse weatherResponse = registerForWeatherData(photovoltaicPanel.getLatitude(),
				photovoltaicPanel.getLongitude());
		photovoltaicPanelsId++;
		photovoltaicPanels.put(photovoltaicPanelsId, photovoltaicPanel);
		return photovoltaicPanelsId;
	}

	public synchronized Map<Long, PhotovoltaicPanel> getPhotovoltaicPanels() {
		return photovoltaicPanels;
	}

	@Override
	public synchronized PhotovoltaicPanel getPhotovoltaicPanel(Long panelId) {
		return photovoltaicPanels.get(panelId);
	}

	@Override
	public synchronized Long addWindTurbine(WindTurbine windTurbine) {
		registerForWeatherData(windTurbine.getLatitude(), windTurbine.getLongitude());
		windTurbineId++;
		getWindTurbines().put(windTurbineId, windTurbine);
		return windTurbineId;
	}

	private synchronized WeatherResponse registerForWeatherData(double latitude, double longitude) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		WeatherRequest weatherRequest = new WeatherRequest(latitude, longitude);
		HttpEntity<WeatherRequest> httpRequest = new HttpEntity<WeatherRequest>(weatherRequest, httpHeaders);
		ResponseEntity<WeatherResponse> weatherResponseEntity = restTemplate.postForEntity(weatherServiceUrl,
				httpRequest, WeatherResponse.class);
		return weatherResponseEntity.getBody();
	}

	@Override
	public synchronized Map<Long, WindTurbine> getWindTurbines() {
		return windTurbines;
	}

	@Override
	public synchronized WindTurbine getWindTurbine(Long turbineId) {
		if (windTurbines.containsKey(turbineId)) {
			return windTurbines.get(turbineId);
		} else {
			throw new IdNotFoundException();
		}
	}

	@Override
	public synchronized double computeEnergyGeneratedPhotovoltaicPanel(Long panelId) {
		WeatherHistory weatherHistory = getMostRecentWeatherHistoryForSupplier(panelId, photovoltaicPanels);
		PhotovoltaicPanel photovoltaicPanel = photovoltaicPanels.get(panelId);
		int dayOfTheYear = getDayOfTheYear(weatherHistory.getTimestamp());
		logger.debug("Calculating photovoltaic panel energy for temp:" + weatherHistory.getTemperature() + " sunpower:"
				+ weatherHistory.getGlobalHorizontalSolarIrradiance() + " dayOfTheYear:" + dayOfTheYear);
		double energy = photovoltaicPanel.computeEnergyGenerated(weatherHistory.getTemperature(),
				weatherHistory.getGlobalHorizontalSolarIrradiance(), dayOfTheYear);
		logger.debug("Result:" + energy);
		return energy;

	}

	@Override
	public synchronized double computeEnergyGeneratedWindTurbine(Long id) {
		WeatherHistory weatherHistory = getMostRecentWeatherHistoryForSupplier(id, windTurbines);
		WindTurbine windTurbine = windTurbines.get(id);
		double energy = windTurbine.computeEnergyGenerated(weatherHistory.getWindSpeed(),
				weatherHistory.getAirPressureInPascal(), weatherHistory.getHumidity(), weatherHistory.getTemperature());
		return energy;
	}

	private synchronized WeatherHistory getMostRecentWeatherHistoryForSupplier(Long id,
			HashMap<Long, ? extends PositionEntity> entityList) {
		if (entityList.containsKey(id)) {
			PositionEntity entity = entityList.get(id);
			logger.debug("Requesting most recent weather data for entity " + id);
			WeatherHistory weatherHistory = weatherForecastRepository
					.findFirstByLatitudeAndLongitudeOrderByTimestampDesc(entity.getLatitude(), entity.getLongitude());
			if (weatherHistory != null) {
				logger.debug("Most recent data is from:" + weatherHistory.getTimestamp());
				return weatherHistory;
			} else {
				throw new NoWeatherDataFoundException();
			}
		} else {
			throw new IdNotFoundException();
		}
	}

	private synchronized int getDayOfTheYear(long timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		int dayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);
		return dayOfTheYear;
	}

	@Override
	public synchronized EnergyForecast computeEnergyGenerateForecastPhotovoltaicPanel(Long id,
			long maxTimestampOffset) {
		List<WeatherForecast> weatherForecastList = getWeatherForecastForSupplier(id, maxTimestampOffset,
				photovoltaicPanels);
		PhotovoltaicPanel photovoltaicPanel = photovoltaicPanels.get(id);
		TreeMap<Long, Double> energyforecastMap = new TreeMap<>();
		weatherForecastList.forEach(weatherforecast -> {
			double energy = photovoltaicPanel.computeEnergyGenerated(weatherforecast.getTemperature(),
					weatherforecast.getGlobalHorizontalSolarIrradiance(),
					getDayOfTheYear(weatherforecast.getTimestamp()));
			energyforecastMap.put(weatherforecast.getTimestamp(), energy);
		});
		EnergyForecast energyForecast = new EnergyForecast();
		logger.debug("Size of generation forecast:" + energyforecastMap.size());
		energyForecast.setForecast(energyforecastMap);
		return energyForecast;

	}

	/**
	 * Solves A2.5
	 */
	@Override
	public synchronized EnergyForecast computeEnergyGenerateForecastWindTurbine(Long id, long maxTimestampOffset) {
		List<WeatherForecast> weatherForecastList = getWeatherForecastForSupplier(id, maxTimestampOffset, windTurbines);
		logger.debug("Got " + weatherForecastList.size() + " forecasts");
		WindTurbine windTurbine = windTurbines.get(id);
		TreeMap<Long, Double> energyforecastMap = new TreeMap<>();
		weatherForecastList.forEach(weatherforecast -> {
			logger.debug("Weatherforecast " + weatherforecast.toString());
			double energy = windTurbine.computeEnergyGenerated(weatherforecast.getWindSpeed(),
					weatherforecast.getAirPressureInPascal(), weatherforecast.getHumidity(),
					weatherforecast.getTemperature());
			logger.debug("Energy for this forecast:" + energy);
			energyforecastMap.put(weatherforecast.getTimestamp(), energy);
		});
		EnergyForecast energyForecast = new EnergyForecast();
		logger.debug("Size of generation forecast:" + energyforecastMap.size());
		energyForecast.setForecast(energyforecastMap);
		return energyForecast;
	}

	private synchronized List<WeatherForecast> getWeatherForecastForSupplier(Long id, long maxTimestampOffset,
			HashMap<Long, ? extends PositionEntity> entityList) {
		if (entityList.containsKey(id)) {
			PositionEntity entity = entityList.get(id);
			logger.debug("Requesting forecast data for entity " + id);
			List<WeatherForecast> weatherForecastList = weatherForecastRepository
					.findByLatitudeAndLongitudeAndTimestampLessThan(entity.getLatitude(), entity.getLongitude(),
							System.currentTimeMillis() + maxTimestampOffset);
			if (weatherForecastList != null && weatherForecastList.size() > 0) {
				return weatherForecastList;
			} else {
				logger.debug("Got none forecasts");
				throw new NoWeatherDataFoundException();
			}
		} else {
			throw new IdNotFoundException();
		}
	}

	private boolean isIdValid(Long id, Map<Long, ?> entities) {
		if (entities.containsKey(id)) {
			return true;
		} else {
			throw new IdNotFoundException();
		}
	}

	@Override
	public synchronized Long addBattery(Battery battery) {
		batteryId++;
		batteries.put(batteryId, battery);
		return batteryId;
	}

	@Override
	public synchronized Battery getBattery(Long id) {
		isIdValid(id, batteries);
		return batteries.get(id);
	}

	@Override
	public synchronized Map<Long, Battery> getBatterys() {
		return batteries;
	}

	@Override
	public synchronized Battery computebatteryChargeProcess(long id, ChargeProcessInput chargeProcessInput) {
		isIdValid(id, batteries);
		Battery battery = batteries.get(id);
		battery.computeStoredEnergy(chargeProcessInput.getDischargingRate(), chargeProcessInput.getChargingRate(),
				chargeProcessInput.getChargingEfficiency());
		return battery;
	}

	@Override
	public synchronized Long addOfficeBuilding(OfficeBuilding officeBuilding) {
		officeBuildingId++;
		officeBuildings.put(officeBuildingId, officeBuilding);
		return officeBuildingId;
	}

	@Override
	public synchronized OfficeBuilding getOfficeBuilding(Long id) {
		isIdValid(id, officeBuildings);
		return officeBuildings.get(id);
	}

	@Override
	public synchronized Map<Long, OfficeBuilding> getOfficeBuildings() {
		return officeBuildings;
	}

	@Override
	public synchronized double computeOfficeBuildingDemand(long id, int hourOfTheDay) {
		isIdValid(id, officeBuildings);
		return officeBuildings.get(id).calculateDemand(hourOfTheDay);
	}

	@Override
	public synchronized void setOfficeBuildingsHourlyBaseDemandPerSquareMeter(long id,double[] hourlyBaseDemandPerSquareMeter) {
		isIdValid(id, officeBuildings);
		officeBuildings.get(id).setHourlyBaseDemandPerSquareMeter(hourlyBaseDemandPerSquareMeter);
	}

	@Override
	public synchronized Long addHomeBuilding(Home home) {
		homeBuildingId++;
		homeBuildings.put(homeBuildingId, home);
		return homeBuildingId;
	}

	@Override
	public synchronized Home getHomeBuilding(long id) {
		isIdValid(id, homeBuildings);
		return homeBuildings.get(id);
	}

	@Override
	public synchronized Map<Long, Home> getHomeBuildings() {
		// TODO Auto-generated method stub
		return homeBuildings;
	}

	@Override
	public synchronized double computeHomeBuildingDemand(long id, int hourOfTheDay) {
		isIdValid(id, homeBuildings);
		return homeBuildings.get(id).calculateDemand(hourOfTheDay);
	}

	@Override
	public synchronized void setHomeBuildingsHourlyBaseDemandPerSquareMeter(long id, double[] hourlyBaseDemandPerSquareMeter) {
		isIdValid(id, homeBuildings);
		homeBuildings.get(id).setHourlyBaseDemandPerSquareMeter(hourlyBaseDemandPerSquareMeter);
	}

}
