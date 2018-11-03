package com.github.smartenergysystem;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.smartenergysystem.model.PhotovoltaicPanelEnergyComuptationInput;
import com.github.smartenergysystem.model.PhotovoltaicPanelWithIdDTO;
import com.github.smartenergysystem.simulation.ISimulationControllerService;
import com.github.smartenergysystem.simulation.PhotovoltaicPanel;
import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SUPPLIER_TAG_NAME})
@RequestMapping("/supplier")
public class SupplierPhotovoltaicPanelSimulationController {

	@Autowired
	ISimulationControllerService simulationControllerService;

	@PostMapping("/photovoltaicPanels")
	public PhotovoltaicPanelWithIdDTO createPhotovoltaicPanel(@RequestBody PhotovoltaicPanel photovoltaicPanel) {
		Long id = simulationControllerService.addPhotovoltaicPanel(photovoltaicPanel);
		PhotovoltaicPanelWithIdDTO photovoltaicPanelWithIdDTO = new PhotovoltaicPanelWithIdDTO();
		BeanUtils.copyProperties(photovoltaicPanel, photovoltaicPanelWithIdDTO);
		photovoltaicPanelWithIdDTO.setId(id);
		return photovoltaicPanelWithIdDTO;
	}
	
	@GetMapping("/photovoltaicPanels")
	public Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels(@RequestBody PhotovoltaicPanel photovoltaicPanel) {
		Map<Long,PhotovoltaicPanel> panels = simulationControllerService.getPhotovoltaicPanels();
		return panels;
	}
	
	@GetMapping("/photovoltaicPanels/{panel-id}")
	public PhotovoltaicPanel getPhotovoltaicPanel(@PathVariable("panel-id") long panelId) {
		return simulationControllerService.getPhotovoltaicPanel(panelId);		
	}
	
	@PostMapping("/photovoltaicPanels/{panel-id}/energyOutput")
	public double getPhotovoltaicPanelEnergyOutput(@PathVariable("panel-id") long panelId,@RequestBody PhotovoltaicPanelEnergyComuptationInput panelEnergyComuptationInput) {
		PhotovoltaicPanel photovoltaicPanel = simulationControllerService.getPhotovoltaicPanel(panelId);
		return photovoltaicPanel.computeEnergyGenerated(panelEnergyComuptationInput.getTemperatureInCelsius(), panelEnergyComuptationInput.getSunpowerHorizontal(), panelEnergyComuptationInput.getDayOfYear());		
	}
	

}
