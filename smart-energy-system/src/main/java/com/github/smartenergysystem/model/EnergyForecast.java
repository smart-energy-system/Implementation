package com.github.smartenergysystem.model;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class EnergyForecast {
	
	
	
	private static final String UNIT = "Wh";
	@ApiModelProperty(value = "The unit of the energy forecast", example = UNIT)
	private String unit = UNIT;
	
	@ApiModelProperty(value = "Map of the timestamps and the values")
	private Map<Long,Double> forecast;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Map<Long, Double> getForecast() {
		return forecast;
	}
	public void setForecast(Map<Long, Double> forecast) {
		this.forecast = forecast;
	}
	
	

}
