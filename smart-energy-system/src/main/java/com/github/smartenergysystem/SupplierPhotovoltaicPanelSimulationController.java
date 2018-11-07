package com.github.smartenergysystem;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.model.PhotovoltaicPanelEnergyComuptationInput;
import com.github.smartenergysystem.model.PhotovoltaicPanelWithIdDTO;
import com.github.smartenergysystem.simulation.ISimulationControllerService;
import com.github.smartenergysystem.simulation.PhotovoltaicPanel;
import com.github.smartenergysystem.simulation.PositionEntity;
import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SUPPLIER_TAG_NAME})
@RequestMapping("/supplier")
public class SupplierPhotovoltaicPanelSimulationController {

	@Autowired
	ISimulationControllerService simulationControllerService;

	@PostMapping("/photovoltaicPanels")
	public PhotovoltaicPanelWithIdDTO addPhotovoltaicPanel(@RequestBody PhotovoltaicPanel photovoltaicPanel) {
		Long id = simulationControllerService.addPhotovoltaicPanel(photovoltaicPanel);
		PhotovoltaicPanelWithIdDTO photovoltaicPanelWithIdDTO = new PhotovoltaicPanelWithIdDTO();
		BeanUtils.copyProperties(photovoltaicPanel, photovoltaicPanelWithIdDTO);
		photovoltaicPanelWithIdDTO.setId(id);
		return photovoltaicPanelWithIdDTO;
	}
	
	@GetMapping("/photovoltaicPanels")
	public Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels() {
		Map<Long,PhotovoltaicPanel> panels = simulationControllerService.getPhotovoltaicPanels();
		return panels;
	}
	
	@GetMapping("/photovoltaicPanels/{id}")
	public PositionEntity getPhotovoltaicPanel(@PathVariable("id") long id) {
		return simulationControllerService.getPhotovoltaicPanel(id);		
	}

	@PostMapping("/photovoltaicPanels/{id}/energyOutput")
	public double getPhotovoltaicPanelEnergyOutput(@PathVariable("id") long id,@RequestBody PhotovoltaicPanelEnergyComuptationInput panelEnergyComuptationInput) {
		PhotovoltaicPanel photovoltaicPanel = simulationControllerService.getPhotovoltaicPanel(id);
		return photovoltaicPanel.computeEnergyGenerated(panelEnergyComuptationInput.getTemperatureInCelsius(), panelEnergyComuptationInput.getSunpowerHorizontal(), panelEnergyComuptationInput.getDayOfYear());		
	}
	
	@GetMapping("/photovoltaicPanels/{id}/energyOutput")
	public double getPhotovoltaicPanelEnergyOutput(@PathVariable("id") long id) {
		return simulationControllerService.computeEnergyGeneratedPhotovoltaicPanel(id);
	}
	
	@GetMapping("/photovoltaicPanels/{id}/energyOutputForecast")
	public EnergyForecast getPhotovoltaicPanelEnergyOutputForecast(@PathVariable("id") long id,@RequestParam(name = "maxTimestampOffset", defaultValue = "86400000") long maxTimestampOffset) {
		return simulationControllerService.computeEnergyGenerateForecastPhotovoltaicPanel(id, maxTimestampOffset);
	}
	
	

}
