package com.github.smartenergysystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
	public List<ConsumerWithIdDTO> getHomeBuildings() {
        Map<Long,Home> homes = homesService.getHomeBuildings();
        List<ConsumerWithIdDTO> homesWithIds = new LinkedList<>();
        for(Map.Entry<Long,Home> homeEntry : homes.entrySet()){
            ConsumerWithIdDTO consumerWithIdDTO = new ConsumerWithIdDTO();
            BeanUtils.copyProperties(homeEntry.getValue(), consumerWithIdDTO);
            consumerWithIdDTO.setId(homeEntry.getKey());
            homesWithIds.add(consumerWithIdDTO);
        }
        return homesWithIds;

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
