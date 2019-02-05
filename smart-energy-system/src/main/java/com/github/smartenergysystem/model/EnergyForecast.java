package com.github.smartenergysystem.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class EnergyForecast {
	
	
	
	private static final String UNIT = "W";
	@ApiModelProperty(value = "The unit of the energy forecast", example = UNIT)
	private String unit = UNIT;
	
	@ApiModelProperty(value = "Map of the timestamps and the values")
	private List<EnergyForecastPoint> forecast;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<EnergyForecastPoint> getForecast() {
		return forecast;
	}

	public void setForecast(List<EnergyForecastPoint> forecast) {
		this.forecast = forecast;
	}
}
