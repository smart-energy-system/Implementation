package com.github.smartenergysystem.simulation;

import io.swagger.annotations.ApiModelProperty;

public class PhotovoltaicPanel {
	public static final double PERFORMANCE_RATIO_DEFAULT = 0.75;
	@ApiModelProperty(required = false)
	private double performanceRatio = PERFORMANCE_RATIO_DEFAULT;
	private double moduleArea;
	private double maximumPowerYield;
	private double tiltAngle;
	private double latitude;
	private double longitude;
	

	public PhotovoltaicPanel(double performanceRatio, double moduleArea, double maximumPowerYield, double tiltAngle,
			double latitude) {
		super();
		this.performanceRatio = performanceRatio;
		this.moduleArea = moduleArea;
		this.maximumPowerYield = maximumPowerYield;
		this.tiltAngle = tiltAngle;
		this.latitude = latitude;
	}
	
	public PhotovoltaicPanel() {};

	public double getLongitude() {
		return longitude;
	}



	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	public double getPerformanceRatio() {
		return performanceRatio;
	}

	public void setPerformanceRatio(double performanceRatio) {
		this.performanceRatio = performanceRatio;
	}

	public double getTiltAngle() {
		return tiltAngle;
	}

	public void setTiltAngle(double tiltAngle) {
		this.tiltAngle = tiltAngle;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getModuleArea() {
		return moduleArea;
	}

	public void setModuleArea(double moduleArea) {
		this.moduleArea = moduleArea;
	}

	public double getMaximumPowerYield() {
		return maximumPowerYield;
	}

	public void setMaximumPowerYield(double maximumPowerYield) {
		this.maximumPowerYield = maximumPowerYield;
	}

	public double computeTemperatureLoss(double temperatureInCelsius) {
		return Math.min(((int) (temperatureInCelsius - 25) * -0.005), 0);
	}

	public double computePerformanceRatio(double temperatureInCelsius) {
		return 1.0 - (0.14 + computeTemperatureLoss(temperatureInCelsius));
	}

	public double computeSolarRadiationIncident(double sunpowerHorizontal, int dayOfYear) {
		double delta = 23.45 * Math.sin((360 / 365) * (284 + dayOfYear));
		double alpha = 90 - getLatitude() + delta;
		return (sunpowerHorizontal * Math.sin(alpha + getTiltAngle())) / Math.sin(alpha);
	}

	public double computeEnergyGenerated(double temperatureInCelsius, double sunpowerHorizontal, int dayOfYear) {
		return getModuleArea() * getMaximumPowerYield() * computePerformanceRatio(temperatureInCelsius)
				* computeSolarRadiationIncident(sunpowerHorizontal, dayOfYear);
	}
}
