package com.github.smartenergysystem;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.smartenergysystem.model.PhotovoltaicPanelWithIdDTO;
import com.github.smartenergysystem.services.WindTurbineService;
import com.github.smartenergysystem.simulation.PhotovoltaicPanel;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.model.WindTurbineEnergyComputationInput;
import com.github.smartenergysystem.model.WindTurbineWithIdDTO;
import com.github.smartenergysystem.simulation.WindTurbine;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SUPPLIER_TAG_NAME})
@RequestMapping("/supplier")
public class SupplierWindTurbineSimulationController {

	Logger logger = LoggerFactory.getLogger(SupplierWindTurbineSimulationController.class);
	public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
	@Autowired
	WindTurbineService windTurbineService;

	@PostMapping("/windTurbines")
	public WindTurbine addWindTurbine(@RequestBody WindTurbine windTurbine) {
		Long id = windTurbineService.addWindTurbine(windTurbine);
		WindTurbineWithIdDTO windTurbineWithIdDTO = new WindTurbineWithIdDTO();
		BeanUtils.copyProperties(windTurbine, windTurbineWithIdDTO);
		windTurbineWithIdDTO.setId(id);
		return windTurbineWithIdDTO;
	}

	@PutMapping("/windTurbines/{id}")
	public void putWindTurbine(@PathVariable("id") long id, @RequestBody WindTurbine windTurbine) {
		windTurbineService.putWindTurbine(id,windTurbine);
	}
	
	@GetMapping("/windTurbines")
	public List<WindTurbine> getWindTurbines() {
		Map<Long, WindTurbine> windTurbines = windTurbineService.getWindTurbines();
		List<WindTurbine> windTurbinesWithIds = new LinkedList<>();
		for(Map.Entry<Long,WindTurbine> windTurbineEntry : windTurbines.entrySet()){
			WindTurbineWithIdDTO windTurbineWithIdDTO = new WindTurbineWithIdDTO();
			BeanUtils.copyProperties(windTurbineEntry.getValue(), windTurbineWithIdDTO);
			windTurbineWithIdDTO.setId(windTurbineEntry.getKey());
			windTurbinesWithIds.add(windTurbineWithIdDTO);
		}
		return windTurbinesWithIds;
	}
	
	@GetMapping("/windTurbines/{id}")
	public WindTurbine getWindTurbine(@PathVariable("id") long id) {
		return windTurbineService.getWindTurbine(id);
	}
	
	@ApiOperation(value = "Returns the energy output for the given input parameters.")
	@PostMapping("/windTurbines/{id}/energyOutput")
	public double getWindTurbineEnergyOutput(@PathVariable("id") long id,
			@RequestBody WindTurbineEnergyComputationInput windTurbineEnergyComputationInput) {
		WindTurbine turbine = windTurbineService.getWindTurbine(id);
		return turbine.computeEnergyGenerated(windTurbineEnergyComputationInput.getWindSpeed(),
				windTurbineEnergyComputationInput.getMeassuredAirPressureInPascal(),
				windTurbineEnergyComputationInput.getRelativeHumidity(),
				windTurbineEnergyComputationInput.getTemperatureInCelsius());
	}
	
	@ApiOperation(value = "Returns the energy output for the most recent values from the database")
	@GetMapping("/windTurbines/{id}/energyOutput")
	public double getWindTurbineEnergyOutput(@PathVariable("id") long id) {
		return windTurbineService.computeEnergyGeneratedWindTurbine(id);
	}

	//	@ApiOperation("Returns the forecast for the production")
//	@GetMapping(value = "/windTurbines/{id}/energyOutputForecast",produces = {MediaType.APPLICATION_JSON_VALUE})
//	public EnergyForecast getWindTurbineEnergyOutputForecast(@PathVariable("id") long id,@RequestParam(name = "maxTimestampOffset", defaultValue = "86400000") long maxTimestampOffset) {
//		System.out.println(Long.MAX_VALUE);
//		logger.trace("Request to WindTubine getWindTurbineEnergyOutputForecast");
//		return windTurbineService.computeEnergyGenerateForecastWindTurbine(id,maxTimestampOffset);
//	}
    @ApiOperation("Returns the forecast for the production")
    @GetMapping(value = "/windTurbines/{id}/energyOutputForecast", produces = {MediaType.APPLICATION_JSON_VALUE})
    public EnergyForecast getWindTurbineEnergyOutputForecast(@PathVariable("id") long id, @ApiParam(name = "startDate", value = "The start date", defaultValue = "2019-01-07T00:00Z")
    @RequestParam("startDate") @DateTimeFormat(pattern = DATE_PATTERN) Date startDate,
                                                             @ApiParam(name = "endDate", value = "The end date", defaultValue = "2019-01-07T23:00Z")
                                                             @RequestParam("endDate") @DateTimeFormat(pattern = DATE_PATTERN) Date endDate) {
        System.out.println(Long.MAX_VALUE);
        logger.trace("Request to WindTubine getWindTurbineEnergyOutputForecast");
        return windTurbineService.computeEnergyGenerateForecastWindTurbine(id, startDate, endDate);
    }

	@DeleteMapping("/windTurbines/{id}")
	public void deleteTurbine(@PathVariable("id") long id) {
		windTurbineService.deleteWindTurbine(id);
	}

}
