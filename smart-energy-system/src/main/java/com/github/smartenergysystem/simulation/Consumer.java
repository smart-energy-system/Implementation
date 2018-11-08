package com.github.smartenergysystem.simulation;

public abstract class Consumer {
	
	private double[] hourlyBaseDemandPerSquareMeter;
	private double floorAreaSize;
	private double averageDailyOccupancy;
	
	public Consumer(double floorAreaSize, double averageDailyOccupancy) {
		super();
		this.floorAreaSize = floorAreaSize;
		this.averageDailyOccupancy = averageDailyOccupancy;
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
	
	public double[] getHourlyBaseDemandPerSquareMeter() {
		return hourlyBaseDemandPerSquareMeter;
	}
	
	public double getBaseDemandPerSquareMeterForHour(int hourOfTheDay) {
		return hourlyBaseDemandPerSquareMeter[hourOfTheDay];
	}

	public void setHourlyBaseDemandPerSquareMeter(double[] hourlyBaseDemandPerSquareMeter) {
		this.hourlyBaseDemandPerSquareMeter = hourlyBaseDemandPerSquareMeter;
	}

	public abstract double calculateDemand(int hourOfTheDay);
	
	

}
