package com.github.smartenergysystem.model;

public class WindTurbineEnergyComputationInput {
	
	private double windSpeed;
	private double meassuredAirPressureInPascal;
	private double relativeHumidity;
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
