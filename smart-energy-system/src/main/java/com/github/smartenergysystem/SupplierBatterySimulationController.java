package com.github.smartenergysystem;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.smartenergysystem.SwaggerConfig;
import com.github.smartenergysystem.model.ConsumerWithIdDTO;
import com.github.smartenergysystem.services.BatteryService;
import com.github.smartenergysystem.simulation.OfficeBuilding;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.smartenergysystem.model.BatteryWithIdDTO;
import com.github.smartenergysystem.model.ChargeProcessInput;
import com.github.smartenergysystem.simulation.Battery;

import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SUPPLIER_TAG_NAME})
@RequestMapping("/supplier")
public class SupplierBatterySimulationController {
	
	@Autowired
	BatteryService batteryService;
	
	@PostMapping("/batteries")
	public BatteryWithIdDTO addBattery(@RequestBody Battery battery) {
		Long id = batteryService.addBattery(battery);
		BatteryWithIdDTO batteryWithIdDTO = new BatteryWithIdDTO();
		BeanUtils.copyProperties(battery, batteryWithIdDTO);
		batteryWithIdDTO.setId(id);
		return batteryWithIdDTO;
	}

	@PutMapping("/batteries/{id}")
	public void putBattery(@PathVariable("id") long id, @RequestBody Battery battery) {
		batteryService.putBattery(id,battery);
	}
	
	@GetMapping("/batteries/{id}")
	public Battery getBattery(@PathVariable("id") long id) {
		return batteryService.getBattery(id);
	}
	
	@GetMapping("/batteries")
	public List<Battery> getBatterys() {
		Map<Long, Battery> batteries = batteryService.getBatterys();
		List<Battery> batteriesWithIds = new LinkedList<>();
		for(Map.Entry<Long,Battery> batteryEntry : batteries.entrySet()){
			BatteryWithIdDTO batteryWithIdDTO = new BatteryWithIdDTO();
			BeanUtils.copyProperties(batteryEntry.getValue(), batteryWithIdDTO);
			batteryWithIdDTO.setId(batteryEntry.getKey());
			batteriesWithIds.add(batteryWithIdDTO);
		}
		return batteriesWithIds;
	}
	
	@PostMapping("/batteries/{id}/ChargeProcess")
	public Battery batteryChargeProcess(@PathVariable("id") long id,@RequestBody ChargeProcessInput chargeProcessInput) {
		return batteryService.computebatteryChargeProcess(id, chargeProcessInput);
	}

	@DeleteMapping("/batteries/{id}")
	public void deleteBatteries(@PathVariable("id") long id) {
		batteryService.deleteBatterie(id);
	}
}
