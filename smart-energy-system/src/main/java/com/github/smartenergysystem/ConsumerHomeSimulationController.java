package com.github.smartenergysystem;

import java.util.Map;

import com.github.smartenergysystem.SwaggerConfig;
import com.github.smartenergysystem.services.HomesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.smartenergysystem.model.ConsumerWithIdDTO;
import com.github.smartenergysystem.simulation.Home;

import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.CONSUMER_TAG_NAME})
@RequestMapping("/consumer/homes")
public class ConsumerHomeSimulationController {
	
	@Autowired
	HomesService homesService;
	
	@PostMapping()
	public ConsumerWithIdDTO addHomeBuilding(@RequestBody Home home) {
		Long id = homesService.addHomeBuilding(home);
		ConsumerWithIdDTO consumerWithIdDTO = new ConsumerWithIdDTO();
		BeanUtils.copyProperties(home, consumerWithIdDTO);
		consumerWithIdDTO.setId(id);
		return consumerWithIdDTO;
	}

	@PutMapping("/{id}")
	public void putHomeBuilding(@PathVariable("id") long id,@RequestBody Home homeNew) {
		homesService.putHome(id,homeNew);
	}
	
	@GetMapping("/{id}")
	public Home getHomeBuilding(@PathVariable("id") long id) {
		return homesService.getHomeBuilding(id);
	}
	
	@GetMapping()
	public Map<Long,Home> getHomeBuildings() {
		return homesService.getHomeBuildings();
	}
	
	@GetMapping("/{id}/energyDemand/{hourOfTheDay}")
	public double getHomeBuildingDemand(@PathVariable("id") long id,@PathVariable("hourOfTheDay") int hourOfTheDay) {
		return homesService.computeHomeBuildingDemand(id, hourOfTheDay);
	}
	
	@PutMapping("/{id}/hourlyBaseDemandPerSquareMeter")
	public void sethourlyBaseDemandPerSquareMeter(@PathVariable("id") long id,@RequestBody double[] hourlyBaseDemandPerSquareMeter) {
		homesService.setHomeBuildingsHourlyBaseDemandPerSquareMeter(id, hourlyBaseDemandPerSquareMeter);
	}

	@DeleteMapping("/{id}")
	public void deleteConsumer(@PathVariable("id") long id) {
		homesService.deleteHome(id);
	}


}
