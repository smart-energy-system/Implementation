package com.github.smartenergysystem.simulation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class Consumer{
	
	private double[] hourlyBaseDemandPerSquareMeter;
	@ApiModelProperty(required = true, example = "1000")
	private double floorAreaSize;
	@ApiModelProperty(required = true, example = "1")
	private double averageDailyOccupancy;
	
	@ApiModelProperty(required = true, example = "0.3")
	private double demandFlexibility;
	
	public Consumer() {};
	
	public Consumer(double floorAreaSize, double averageDailyOccupancy) {
		super();
		this.floorAreaSize = floorAreaSize;
		this.averageDailyOccupancy = averageDailyOccupancy;
	}
	
	public double getDemandFlexibility() {
		return demandFlexibility;
	}

	public void setDemandFlexibility(double demandFlexibility) {
		this.demandFlexibility = demandFlexibility;
	}

	public double getFloorAreaSize() {
		return floorAreaSize;
	}
	public void setFloorAreaSize(double floorAreaSize) {
		this.floorAreaSize = floorAreaSize;
	}
	public double getAverageDailyOccupancy() {
		return averageDailyOccupancy;
	}
	public void setAverageDailyOccupancy(double averageDailyOccupancy) {
		this.averageDailyOccupancy = averageDailyOccupancy;
	}
	
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public double[] getHourlyBaseDemandPerSquareMeter() {
		return hourlyBaseDemandPerSquareMeter;
	}
	
	public double getBaseDemandPerSquareMeterForHour(int hourOfTheDay) {
		return hourlyBaseDemandPerSquareMeter[hourOfTheDay];
	}

	public void setHourlyBaseDemandPerSquareMeter(double[] hourlyBaseDemandPerSquareMeter) {
		this.hourlyBaseDemandPerSquareMeter = hourlyBaseDemandPerSquareMeter;
	}

	/**
	 * Solves A2.11
	 * @param hourOfTheDay
	 * @return the demand in kWh
	 */
	public double calculateDemand(int hourOfTheDay) {
		return this.getBaseDemandPerSquareMeterForHour(hourOfTheDay)* this.getFloorAreaSize() * this.getAverageDailyOccupancy();
	}
	
	

}
