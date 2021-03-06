package com.github.smartenergysystem;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.smartenergysystem.SwaggerConfig;
import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.services.OfficeBuildingService;
import com.github.smartenergysystem.simulation.Home;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.github.smartenergysystem.model.ConsumerWithIdDTO;
import com.github.smartenergysystem.simulation.OfficeBuilding;

import io.swagger.annotations.Api;

import static com.github.smartenergysystem.SupplierWindTurbineSimulationController.DATE_PATTERN;

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
	public List<ConsumerWithIdDTO> getOfficeBuildings() {
		Map<Long, OfficeBuilding> officeBuildings = officeBuildingService.getOfficeBuildings();
		List<ConsumerWithIdDTO> officeWithIds = new LinkedList<>();
		for(Map.Entry<Long,OfficeBuilding> officeEntry : officeBuildings.entrySet()){
			ConsumerWithIdDTO consumerWithIdDTO = new ConsumerWithIdDTO();
			BeanUtils.copyProperties(officeEntry.getValue(), consumerWithIdDTO);
			consumerWithIdDTO.setId(officeEntry.getKey());
			officeWithIds.add(consumerWithIdDTO);
		}
		return officeWithIds;
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

	@GetMapping("/{id}/demandForecast")
	public EnergyForecast getOfficeBuildingDemandForecast(@PathVariable("id") long id,@ApiParam(name = "startDate", value = "The start date", defaultValue = "2019-01-07T00:00Z")
	@RequestParam("startDate") @DateTimeFormat(pattern = DATE_PATTERN) Date startDate,
														  @ApiParam(name = "endDate", value = "The end date", defaultValue = "2019-01-07T23:00Z")
															  @RequestParam("endDate") @DateTimeFormat(pattern = DATE_PATTERN) Date endDate) {
		OfficeBuilding officeBuilding = officeBuildingService.getOfficeBuilding(id);
		return officeBuildingService.getDemandForecast(officeBuilding,startDate,endDate);
	}

}
