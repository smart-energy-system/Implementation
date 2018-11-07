package com.github.smartenergysystem.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.ApiModelProperty;

public class PhotovoltaicPanel extends PositionEntity {
	
	/**
	 * Accounts for all constant losses like cable loss
	 */
	private static final double L0 = 0.14; 

	Logger logger = LoggerFactory.getLogger(PhotovoltaicPanel.class);
	
	public static final double PERFORMANCE_RATIO_DEFAULT = 0.75;
	@ApiModelProperty(required = false, example = "0.75")
	private double performanceRatio = PERFORMANCE_RATIO_DEFAULT;
	@ApiModelProperty(required = true, example = "1")
	private double moduleArea;
	@ApiModelProperty(required = true, example = "260")
	private double maximumPowerYield;
	@ApiModelProperty(required = true, example = "0.3")
	private double tiltAngle;

	public PhotovoltaicPanel(double performanceRatio, double moduleArea, double maximumPowerYield, double tiltAngle,
			double latitude, double longitude) {
		super();
		this.performanceRatio = performanceRatio;
		this.moduleArea = moduleArea;
		this.maximumPowerYield = maximumPowerYield;
		this.tiltAngle = tiltAngle;
		this.latitude = latitude;
	}

	public PhotovoltaicPanel() {
	};

	public double getPerformanceRatio() {
		return performanceRatio;
	}

	public void setPerformanceRatio(double performanceRatio) {
		if (performanceRatio > 0) {
			this.performanceRatio = performanceRatio;
		} else {
			performanceRatio = PERFORMANCE_RATIO_DEFAULT;
		}
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
	
	/**
	 * Solves A2.6.
	 * @param temperatureInCelsius in degrees Celsius
	 * @return positive percentage of energyloss as double: 0.13 for example
	 */
	public double computeTemperatureLoss(double temperatureInCelsius) {
		return Math.max(((int) (temperatureInCelsius - 25) * 0.005), 0);
	}
	
	/**
	 * Solves A2.7.
	 * @param temperatureInCelsius in degrees Celsius
	 * @return ratio between 0 and 1 for performance
	 */
	public double computeTotalLosses(double temperatureInCelsius) {
		return L0 + computeTemperatureLoss(temperatureInCelsius);
	}
	
	/**
	 * Solves A2.8.
	 * @param sunpowerHorizontal in W/(m^2)
	 * @param dayOfYear as Integer
	 * @return solarradiation incident in W/(m^2)
	 */
	public double computeSolarRadiationIncident(double sunpowerHorizontal, int dayOfYear) {
		double delta = 23.45 * Math.sin((360 / 365) * (284 + dayOfYear));
		double alpha = 90 - getLatitude() + delta;
		return (sunpowerHorizontal * Math.sin(alpha + getTiltAngle())) / Math.sin(alpha);
	}

	/**
	 * Solves A2.9.
	 * @param temperatureInCelsius in degree Celsius
	 * @param sunpowerHorizontal in W/(m^2)
	 * @param dayOfYear as Integer
	 * @return energy generated in W
	 */
	public double computeEnergyGenerated(double temperatureInCelsius, double sunpowerHorizontal, int dayOfYear) {
		logger.debug("Calculate energy for a photovoltaic panel  with temperatureInCelsius:" + temperatureInCelsius
				+ " sunpowerHorizontal:" + sunpowerHorizontal + " dayOfYear:" + dayOfYear);
		double performanceRatio = 1 - computeTotalLosses(temperatureInCelsius);
		return getModuleArea() * getMaximumPowerYield() * performanceRatio
				* computeSolarRadiationIncident(sunpowerHorizontal, dayOfYear);
	}

}
