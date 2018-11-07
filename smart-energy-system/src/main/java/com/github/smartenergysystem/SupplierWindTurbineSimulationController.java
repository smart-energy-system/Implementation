package com.github.smartenergysystem;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.model.WindTurbineEnergyComputationInput;
import com.github.smartenergysystem.model.WindTurbineWithIdDTO;
import com.github.smartenergysystem.simulation.ISimulationControllerService;
import com.github.smartenergysystem.simulation.SimulationControllerService;
import com.github.smartenergysystem.simulation.WindTurbine;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SUPPLIER_TAG_NAME})
@RequestMapping("/supplier")
public class SupplierWindTurbineSimulationController {
	
	Logger logger = LoggerFactory.getLogger(SupplierWindTurbineSimulationController.class);
	
	@Autowired
	ISimulationControllerService simulationControllerService;

	@PostMapping("/windTurbines")
	public WindTurbine addWindTurbine(@RequestBody WindTurbine windTurbine) {
		Long id = simulationControllerService.addWindTurbine(windTurbine);
		WindTurbineWithIdDTO windTurbineWithIdDTO = new WindTurbineWithIdDTO();
		BeanUtils.copyProperties(windTurbine, windTurbineWithIdDTO);
		windTurbineWithIdDTO.setId(id);
		return windTurbineWithIdDTO;
	}
	
	@GetMapping("/windTurbines")
	public Map<Long,WindTurbine> getWindTurbines() {
		Map<Long,WindTurbine> turbines = simulationControllerService.getWindTurbines();
		return turbines;
	}
	
	@GetMapping("/windTurbines/{id}")
	public WindTurbine getWindTurbine(@PathVariable("id") long id) {
		return simulationControllerService.getWindTurbine(id);		
	}
	
	@ApiOperation(value = "Returns the energy output for the given input parameters.")
	@PostMapping("/windTurbines/{id}/energyOutput")
	public double getWindTurbineEnergyOutput(@PathVariable("id") long id,@RequestBody WindTurbineEnergyComputationInput windTurbineEnergyComputationInput) {
		WindTurbine turbine = simulationControllerService.getWindTurbine(id);
		return turbine.computeEnergyGenerated(windTurbineEnergyComputationInput.getWindSpeed(), windTurbineEnergyComputationInput.getMeassuredAirPressureInPascal(), windTurbineEnergyComputationInput.getRelativeHumidity(), windTurbineEnergyComputationInput.getTemperatureInCelsius());
	}
	
	@ApiOperation(value = "Returns the energy output for the most recent values from the database")
	@GetMapping("/windTurbines/{id}/energyOutput")
	public double getWindTurbineEnergyOutput(@PathVariable("id") long id) {
		return simulationControllerService.computeEnergyGeneratedWindTurbine(id);
	}
	
	@ApiOperation("Returns the forecast for the production")
	@GetMapping(value = "/windTurbines/{id}/energyOutputForecast",produces = {MediaType.APPLICATION_JSON_VALUE})
	public EnergyForecast getWindTurbineEnergyOutputForecast(@PathVariable("id") long id,@RequestParam(name = "maxTimestampOffset", defaultValue = "86400000") long maxTimestampOffset) {
		System.out.println(Long.MAX_VALUE);
		logger.trace("Request to WindTubine getWindTurbineEnergyOutputForecast");
		return simulationControllerService.computeEnergyGenerateForecastWindTurbine(id,maxTimestampOffset);
	}

}
