package com.github.smartenergysystem;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.smartenergysystem.model.ChargeProcessInput;
import com.github.smartenergysystem.simulation.Battery;
import com.github.smartenergysystem.simulation.ISimulationControllerService;
import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SUPPLIER_TAG_NAME})
@RequestMapping("/supplier")
public class SupplierBatterySimulationController {
	
	@Autowired
	ISimulationControllerService simulationControllerService;
	
	@PostMapping("/batteries")
	public Battery addBattery(@RequestBody Battery battery) {
		return simulationControllerService.addBattery(battery);
	}
	
	@GetMapping("/batteries/{id}")
	public Battery getBattery(@PathVariable("id") long id) {
		return simulationControllerService.getBattery(id);	
	}
	
	@GetMapping("/batteries")
	public Collection<Battery> getBatterys() {
		return simulationControllerService.getBatterys();	
	}
	
	@PostMapping("/batteries/{id}/ChargeProcess")
	public Battery batteryChargeProcess(@PathVariable("id") long id,@RequestBody ChargeProcessInput chargeProcessInput) {
		return simulationControllerService.computebatteryChargeProcess(id, chargeProcessInput);	
	}

}
