package com.github.smartenergysystem;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.smartenergysystem.SwaggerConfig;
import com.github.smartenergysystem.model.BatteryWithIdDTO;
import com.github.smartenergysystem.services.PhotovoltaicPanelsService;
import com.github.smartenergysystem.simulation.Battery;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.github.smartenergysystem.model.EnergyForecast;
import com.github.smartenergysystem.model.PhotovoltaicPanelEnergyComuptationInput;
import com.github.smartenergysystem.model.PhotovoltaicPanelWithIdDTO;
import com.github.smartenergysystem.simulation.PhotovoltaicPanel;
import io.swagger.annotations.Api;

import static com.github.smartenergysystem.SupplierWindTurbineSimulationController.DATE_PATTERN;

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

	@PutMapping("/photovoltaicPanels/{id}")
	public void putPhotovoltaicPanel(@PathVariable("id") long id, @RequestBody PhotovoltaicPanel photovoltaicPanel) {
		photovoltaicPanelsService.putPhotovoltaicPanel(id,photovoltaicPanel);
	}
	
	@GetMapping("/photovoltaicPanels")
	public List<PhotovoltaicPanel> getPhotovoltaicPanels() {
		Map<Long, PhotovoltaicPanel> photovoltaicPanels = photovoltaicPanelsService.getPhotovoltaicPanels();
		List<PhotovoltaicPanel> photovoltaicPanelsWithIds = new LinkedList<>();
		for(Map.Entry<Long,PhotovoltaicPanel> photovoltaicPanelsEntry : photovoltaicPanels.entrySet()){
			PhotovoltaicPanelWithIdDTO photovoltaicPanelWithIdDTO = new PhotovoltaicPanelWithIdDTO();
			BeanUtils.copyProperties(photovoltaicPanelsEntry.getValue(), photovoltaicPanelWithIdDTO);
			photovoltaicPanelWithIdDTO.setId(photovoltaicPanelsEntry.getKey());
			photovoltaicPanelsWithIds.add(photovoltaicPanelWithIdDTO);
		}
		return photovoltaicPanelsWithIds;
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
	
/*	@GetMapping("/photovoltaicPanels/{id}/energyOutputForecast")
	public EnergyForecast getPhotovoltaicPanelEnergyOutputForecast(@PathVariable("id") long id,@RequestParam(name = "maxTimestampOffset", defaultValue = "86400000") long maxTimestampOffset) {
		return photovoltaicPanelsService.computeEnergyGenerateForecastPhotovoltaicPanel(id, maxTimestampOffset);
	}*/

	@GetMapping("/photovoltaicPanels/{id}/energyOutputForecast")
	public EnergyForecast getPhotovoltaicPanelEnergyOutputForecast(@PathVariable("id") long id,@ApiParam(name = "startDate", value = "The start date", defaultValue = "2019-01-07T00:00Z")
	@RequestParam("startDate") @DateTimeFormat(pattern = DATE_PATTERN) Date startDate,
																   @ApiParam(name = "endDate", value = "The end date", defaultValue = "2019-01-07T23:00Z")
																	   @RequestParam("endDate") @DateTimeFormat(pattern = DATE_PATTERN) Date endDate) {
		return photovoltaicPanelsService.computeEnergyGenerateForecastPhotovoltaicPanel(id, startDate,endDate);
	}

	@DeleteMapping("/photovoltaicPanels/{id}")
	public void deletePanel(@PathVariable("id") long id) {
		photovoltaicPanelsService.deletePhotovoltaicPanel(id);
	}
	

}
