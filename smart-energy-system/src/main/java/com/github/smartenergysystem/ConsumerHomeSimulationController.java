package com.github.smartenergysystem;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.smartenergysystem.model.ConsumerWithIdDTO;
import com.github.smartenergysystem.simulation.Home;
import com.github.smartenergysystem.simulation.ISimulationControllerService;
import com.github.smartenergysystem.simulation.OfficeBuilding;

import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.CONSUMER_TAG_NAME})
@RequestMapping("/consumer/homes")
public class ConsumerHomeSimulationController {
	
	@Autowired
	ISimulationControllerService simulationControllerService;
	
	@PostMapping()
	public ConsumerWithIdDTO addHomeBuilding(@RequestBody Home home) {
		Long id = simulationControllerService.addHomeBuilding(home);
		ConsumerWithIdDTO consumerWithIdDTO = new ConsumerWithIdDTO();
		BeanUtils.copyProperties(home, consumerWithIdDTO);
		consumerWithIdDTO.setId(id);
		return consumerWithIdDTO;
	}
	
	@GetMapping("/{id}")
	public Home getHomeBuilding(@PathVariable("id") long id) {
		return simulationControllerService.getHomeBuilding(id);	
	}
	
	@GetMapping()
	public Map<Long,Home> getHomeBuildings() {
		return simulationControllerService.getHomeBuildings();	
	}
	
	@GetMapping("/{id}/energyDemand/{hourOfTheDay}")
	public double getHomeBuildingDemand(@PathVariable("id") long id,@PathVariable("hourOfTheDay") int hourOfTheDay) {
		return simulationControllerService.computeHomeBuildingDemand(id, hourOfTheDay);
	}
	
	@PutMapping("/{id}/hourlyBaseDemandPerSquareMeter")
	public void sethourlyBaseDemandPerSquareMeter(@PathVariable("id") long id,@RequestBody double[] hourlyBaseDemandPerSquareMeter) {
		simulationControllerService.setHomeBuildingsHourlyBaseDemandPerSquareMeter(id, hourlyBaseDemandPerSquareMeter);
	}


}
