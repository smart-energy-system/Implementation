package com.github.smartenergysystem;

import java.util.Map;

import com.github.smartenergysystem.services.PhotovoltaicPanelsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.model.PhotovoltaicPanelEnergyComuptationInput;
import com.github.smartenergysystem.model.PhotovoltaicPanelWithIdDTO;
import com.github.smartenergysystem.simulation.PhotovoltaicPanel;
import io.swagger.annotations.Api;

@RestController
@Api(produces ="application/json",tags = { SwaggerConfig.SUPPLIER_TAG_NAME})
@RequestMapping("/supplier")
public class SupplierPhotovoltaicPanelSimulationController {

	@Autowired
	PhotovoltaicPanelsService photovoltaicPanelsService;

	@PostMapping("/photovoltaicPanels")
	public PhotovoltaicPanelWithIdDTO addPhotovoltaicPanel(@RequestBody PhotovoltaicPanel photovoltaicPanel) {
		Long id = photovoltaicPanelsService.addPhotovoltaicPanel(photovoltaicPanel);
		PhotovoltaicPanelWithIdDTO photovoltaicPanelWithIdDTO = new PhotovoltaicPanelWithIdDTO();
		BeanUtils.copyProperties(photovoltaicPanel, photovoltaicPanelWithIdDTO);
		photovoltaicPanelWithIdDTO.setId(id);
		return photovoltaicPanelWithIdDTO;
	}
	
	@GetMapping("/photovoltaicPanels")
	public Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels() {
		Map<Long,PhotovoltaicPanel> panels = photovoltaicPanelsService.getPhotovoltaicPanels();
		return panels;
	}
	
	@GetMapping("/photovoltaicPanels/{id}")
	public PhotovoltaicPanel getPhotovoltaicPanel(@PathVariable("id") long id) {
		return photovoltaicPanelsService.getPhotovoltaicPanel(id);
	}

	@PostMapping("/photovoltaicPanels/{id}/energyOutput")
	public double getPhotovoltaicPanelEnergyOutput(@PathVariable("id") long id,@RequestBody PhotovoltaicPanelEnergyComuptationInput panelEnergyComuptationInput) {
		PhotovoltaicPanel photovoltaicPanel = photovoltaicPanelsService.getPhotovoltaicPanel(id);
		return photovoltaicPanel.computeEnergyGenerated(panelEnergyComuptationInput.getTemperatureInCelsius(), panelEnergyComuptationInput.getSunpowerHorizontal(), panelEnergyComuptationInput.getDayOfYear());		
	}
	
	@GetMapping("/photovoltaicPanels/{id}/energyOutput")
	public double getPhotovoltaicPanelEnergyOutput(@PathVariable("id") long id) {
		return photovoltaicPanelsService.computeEnergyGeneratedPhotovoltaicPanel(id);
	}
	
	@GetMapping("/photovoltaicPanels/{id}/energyOutputForecast")
	public EnergyForecast getPhotovoltaicPanelEnergyOutputForecast(@PathVariable("id") long id,@RequestParam(name = "maxTimestampOffset", defaultValue = "86400000") long maxTimestampOffset) {
		return photovoltaicPanelsService.computeEnergyGenerateForecastPhotovoltaicPanel(id, maxTimestampOffset);
	}

	@DeleteMapping("/photovoltaicPanels/{id}")
	public void deletePanel(@PathVariable("id") long id) {
		photovoltaicPanelsService.deletePhotovoltaicPanel(id);
	}
	

}
