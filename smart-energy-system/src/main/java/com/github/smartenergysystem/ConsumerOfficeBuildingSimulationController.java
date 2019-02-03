package com.github.smartenergysystem;
import java.util.Map;

import com.github.smartenergysystem.SwaggerConfig;
import com.github.smartenergysystem.services.OfficeBuildingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.smartenergysystem.model.ConsumerWithIdDTO;
import com.github.smartenergysystem.simulation.OfficeBuilding;

import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.CONSUMER_TAG_NAME})
@RequestMapping("/consumer/officeBuildings")
public class ConsumerOfficeBuildingSimulationController {
	
	@Autowired
	OfficeBuildingService officeBuildingService;
	
	@PostMapping()
	public ConsumerWithIdDTO addOfficeBuilding(@RequestBody OfficeBuilding officeBuilding) {
		Long id = officeBuildingService.addOfficeBuilding(officeBuilding);
		ConsumerWithIdDTO consumerWithIdDTO = new ConsumerWithIdDTO();
		BeanUtils.copyProperties(officeBuilding, consumerWithIdDTO);
		consumerWithIdDTO.setId(id);
		return consumerWithIdDTO;
	}

	@PutMapping("/{id}")
	public void putOfficeBuilding(@PathVariable("id") long id,@RequestBody OfficeBuilding officeBuilding) {
		officeBuildingService.putOffice(id,officeBuilding);
	}
	
	@GetMapping("/{id}")
	public OfficeBuilding getOfficeBuilding(@PathVariable("id") long id) {
		return officeBuildingService.getOfficeBuilding(id);
	}
	
	@GetMapping()
	public Map<Long,OfficeBuilding> getOfficeBuildings() {
		return officeBuildingService.getOfficeBuildings();
	}
	
	@GetMapping("/{id}/energyDemand/{hourOfTheDay}")
	public double getOfficeBuildingDemand(@PathVariable("id") long id,@PathVariable("hourOfTheDay") int hourOfTheDay) {
		return officeBuildingService.computeOfficeBuildingDemand(id, hourOfTheDay);
	}
	
	@PutMapping("/{id}/hourlyBaseDemandPerSquareMeter")
	public void sethourlyBaseDemandPerSquareMeter(@PathVariable("id") long id,@RequestBody double[] hourlyBaseDemandPerSquareMeter) {
		officeBuildingService.setOfficeBuildingsHourlyBaseDemandPerSquareMeter(id, hourlyBaseDemandPerSquareMeter);
	}

	@DeleteMapping("/{id}")
	public void deleteOfficeBuilding(@PathVariable("id") long id) {
		officeBuildingService.deleteOfficeBuilding(id);
	}

}
