package com.github.smartenergysystem.model;

import io.swagger.annotations.ApiModelProperty;

public class WindTurbineEnergyComputationInput {
	
	@ApiModelProperty(value = "The current wind speed, measured in meter per second.", example = "5")
	private double windSpeed;
	
	@ApiModelProperty(value = "The current wind speed, measured in meter per second.", example = "1020000")
	private double meassuredAirPressureInPascal;
	
	@ApiModelProperty(value = "The relative humidity.", example = "0.74")
	private double relativeHumidity;
	
	@ApiModelProperty(value = "The temperature in celsius.", example = "25")
	private double temperatureInCelsius;
	
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public double getMeassuredAirPressureInPascal() {
		return meassuredAirPressureInPascal;
	}
	public void setMeassuredAirPressureInPascal(double meassuredAirPressureInPascal) {
		this.meassuredAirPressureInPascal = meassuredAirPressureInPascal;
	}
	public double getRelativeHumidity() {
		return relativeHumidity;
	}
	public void setRelativeHumidity(double relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}
	public double getTemperatureInCelsius() {
		return temperatureInCelsius;
	}
	public void setTemperatureInCelsius(double temperatureInCelsius) {
		this.temperatureInCelsius = temperatureInCelsius;
	}
	
	

}
