package com.github.smartenergysystem.simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.weather.WeatherForecast;
import com.github.smartenergysystem.weather.WeatherForecastRepository;
import com.github.smartenergysystem.weather.WeatherRequest;
import com.github.smartenergysystem.weather.WeatherResponse;

@Service
public class SimulationControllerService implements ISimulationControllerService {

	Logger logger = LoggerFactory.getLogger(SimulationControllerService.class);

	@Autowired
	WeatherForecastRepository weatherForecastRepository;

	@Value("${weatherService.url}")
	String weatherServiceUrl;

	// Replace with a database after we decided which database we use
	// probably no need for atomic types because of synchronized methods
	Long photovoltaicPanelsId = 0L;
	HashMap<Long, PhotovoltaicPanel> photovoltaicPanels = new HashMap<>();

	Long windTurbineId = 0L;
	HashMap<Long, WindTurbine> windTurbines = new HashMap<>();

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
	public PhotovoltaicPanel getPhotovoltaicPanel(Long panelId) {
		return photovoltaicPanels.get(panelId);
	}

	@Override
	public Long addWindTurbine(WindTurbine windTurbine) {
		registerForWeatherData(windTurbine.getLatitude(), windTurbine.getLongitude());
		windTurbineId++;
		getWindTurbines().put(windTurbineId, windTurbine);
		return windTurbineId;
	}

	private WeatherResponse registerForWeatherData(double latitude, double longitude) {
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
	public Map<Long, WindTurbine> getWindTurbines() {
		return windTurbines;
	}

	@Override
	public WindTurbine getWindTurbine(Long turbineId) {
		return windTurbines.get(turbineId);
	}

	@Override
	public double computeEnergyGeneratedPhotovoltaicPanel(Long panelId) {
		PhotovoltaicPanel photovoltaicPanel = photovoltaicPanels.get(panelId);
		// Get From DB
		// photovoltaicPanel.computeEnergyGenerated(temperatureInCelsius,
		// sunpowerHorizontal, dayOfYear)
		return 0;
	}

	@Override
	public double computeEnergyGeneratedWindTurbine(Long panelId) {
		return 0;
	}

	@Override
	public EnergyForecast computeEnergyGenerateForecastPhotovoltaicPanel(Long id) {
		return null;
	}

	@Override
	public EnergyForecast computeEnergyGenerateForecastWindTurbine(Long id) {
		WindTurbine windTurbine = windTurbines.get(id);
		logger.debug("Requesting forecast data for wind turbine " + id);
		List<WeatherForecast> weatherForecastList = weatherForecastRepository
				.findByLatitudeAndLongitude(windTurbine.latitude, windTurbine.longitude);
		if (weatherForecastList != null && weatherForecastList.size() > 0) {
			logger.debug("Got " + weatherForecastList.size() + " forecasts");
			EnergyForecast energyForecast = new EnergyForecast();
			HashMap<Long, Double> energyforecastList = new HashMap<>();
			weatherForecastList.forEach(weatherforecast -> {
				logger.debug("Weatherforecast " + weatherforecast.toString());
				double airPressureInPascal = weatherforecast.getAirPressure() * WindTurbine.CONVERT_hPA_TO_PA;
				double energy = windTurbine.computeEnergyGenerated(weatherforecast.getWindSpeed(), airPressureInPascal,
						weatherforecast.getHumidity(), weatherforecast.getTemperature());
				logger.debug("Energy for this forecast:" + energy);
				energyforecastList.put(weatherforecast.getTimestamp(),energy);
			});
			energyForecast.setForecast(energyforecastList);
			return energyForecast;
		}
		logger.debug("Got none forecasts");
		return null;
	}

	@Override
	public boolean existsPhotovoltaicPanel(Long panelId) {
		return photovoltaicPanels.containsKey(panelId);
	}

	@Override
	public boolean existsWindTurbine(Long turbineId) {
		return windTurbines.containsKey(turbineId);
	}

}
