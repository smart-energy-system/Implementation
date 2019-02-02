package com.github.smartenergysystem;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.smartenergysystem.model.ConsumerWithIdDTO;
import com.github.smartenergysystem.simulation.ISimulationControllerService;
import com.github.smartenergysystem.simulation.OfficeBuilding;

import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.CONSUMER_TAG_NAME})
@RequestMapping("/consumer/officeBuildings")
public class ConsumerOfficeBuildingSimulationController {
	
	@Autowired
	ISimulationControllerService simulationControllerService;
	
	@PostMapping()
	public ConsumerWithIdDTO addOfficeBuilding(@RequestBody OfficeBuilding officeBuilding) {
		Long id = simulationControllerService.addOfficeBuilding(officeBuilding);
		ConsumerWithIdDTO consumerWithIdDTO = new ConsumerWithIdDTO();
		BeanUtils.copyProperties(officeBuilding, consumerWithIdDTO);
		consumerWithIdDTO.setId(id);
		return consumerWithIdDTO;
	}
	
	@GetMapping("/{id}")
	public OfficeBuilding getOfficeBuilding(@PathVariable("id") long id) {
		return simulationControllerService.getOfficeBuilding(id);	
	}
	
	@GetMapping()
	public Map<Long,OfficeBuilding> getOfficeBuildings() {
		return simulationControllerService.getOfficeBuildings();	
	}
	
	@GetMapping("/{id}/energyDemand/{hourOfTheDay}")
	public double getOfficeBuildingDemand(@PathVariable("id") long id,@PathVariable("hourOfTheDay") int hourOfTheDay) {
		return simulationControllerService.computeOfficeBuildingDemand(id, hourOfTheDay);
	}
	
	@PutMapping("/{id}/hourlyBaseDemandPerSquareMeter")
	public void sethourlyBaseDemandPerSquareMeter(@PathVariable("id") long id,@RequestBody double[] hourlyBaseDemandPerSquareMeter) {
		simulationControllerService.setOfficeBuildingsHourlyBaseDemandPerSquareMeter(id, hourlyBaseDemandPerSquareMeter);
	}

	@DeleteMapping("/{id}")
	public void deleteOfficeBuilding(@PathVariable("id") long id) {
		simulationControllerService.deleteOfficeBuilding(id);
	}

}
