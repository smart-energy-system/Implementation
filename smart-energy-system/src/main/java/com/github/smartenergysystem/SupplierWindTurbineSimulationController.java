package com.github.smartenergysystem;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.smartenergysystem.model.WindTurbineEnergyComputationInput;
import com.github.smartenergysystem.model.WindTurbineWithIdDTO;
import com.github.smartenergysystem.simulation.ISimulationControllerService;
import com.github.smartenergysystem.simulation.WindTurbine;

import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SUPPLIER_TAG_NAME})
@RequestMapping("/supplier")
public class SupplierWindTurbineSimulationController {
	
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
	
	@PostMapping("/windTurbines/{id}/energyOutput")
	public double getWindTurbineEnergyOutput(@PathVariable("id") long id,@RequestBody WindTurbineEnergyComputationInput windTurbineEnergyComputationInput) {
		WindTurbine turbine = simulationControllerService.getWindTurbine(id);
		return turbine.computeEnergyGenerated(windTurbineEnergyComputationInput.getWindSpeed(), windTurbineEnergyComputationInput.getMeassuredAirPressureInPascal(), windTurbineEnergyComputationInput.getRelativeHumidity(), windTurbineEnergyComputationInput.getTemperatureInCelsius());
	}
	
	@GetMapping("/windTurbines/{id}/energyOutput")
	public double getPhotovoltaicPanelEnergyOutput(@PathVariable("id") long id) {
		return simulationControllerService.computeEnergyGeneratedWindTurbine(id);
	}

}
