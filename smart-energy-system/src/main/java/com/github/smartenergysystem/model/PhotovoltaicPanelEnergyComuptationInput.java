package com.github.smartenergysystem.model;

public class PhotovoltaicPanelEnergyComuptationInput {
	
	private int temperatureInCelsius;
	private int sunpowerHorizontal;
	private int dayOfYear;
	public int getTemperatureInCelsius() {
		return temperatureInCelsius;
	}
	public void setTemperatureInCelsius(int temperatureInCelsius) {
		this.temperatureInCelsius = temperatureInCelsius;
	}
	public int getSunpowerHorizontal() {
		return sunpowerHorizontal;
	}
	public void setSunpowerHorizontal(int sunpowerHorizontal) {
		this.sunpowerHorizontal = sunpowerHorizontal;
	}
	public int getDayOfYear() {
		return dayOfYear;
	}
	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}
	
	

}
