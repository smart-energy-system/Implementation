package com.github.smartenergysystem;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.smartenergysystem.model.BatteryWithIdDTO;
import com.github.smartenergysystem.model.ChargeProcessInput;
import com.github.smartenergysystem.simulation.Battery;
import com.github.smartenergysystem.simulation.ISimulationControllerService;
import com.github.smartenergysystem.simulation.PhotovoltaicPanel;

import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SUPPLIER_TAG_NAME})
@RequestMapping("/supplier")
public class SupplierBatterySimulationController {
	
	@Autowired
	ISimulationControllerService simulationControllerService;
	
	@PostMapping("/batteries")
	public BatteryWithIdDTO addBattery(@RequestBody Battery battery) {
		Long id = simulationControllerService.addBattery(battery);
		BatteryWithIdDTO batteryWithIdDTO = new BatteryWithIdDTO();
		BeanUtils.copyProperties(battery, batteryWithIdDTO);
		batteryWithIdDTO.setId(id);
		return batteryWithIdDTO;
	}
	
	@GetMapping("/batteries/{id}")
	public Battery getBattery(@PathVariable("id") long id) {
		return simulationControllerService.getBattery(id);	
	}
	
	@GetMapping("/batteries")
	public Map<Long,Battery> getBatterys() {
		return simulationControllerService.getBatterys();	
	}
	
	@PostMapping("/batteries/{id}/ChargeProcess")
	public Battery batteryChargeProcess(@PathVariable("id") long id,@RequestBody ChargeProcessInput chargeProcessInput) {
		return simulationControllerService.computebatteryChargeProcess(id, chargeProcessInput);	
	}

	@DeleteMapping("/batteries/{id}")
	public void deleteBatteries(@PathVariable("id") long id) {
		simulationControllerService.deleteBatterie(id);
	}
}
