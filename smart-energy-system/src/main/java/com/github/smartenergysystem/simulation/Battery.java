package com.github.smartenergysystem.simulation;

import io.swagger.annotations.ApiModelProperty;

public class Battery {

	@ApiModelProperty(required = true, example = "12000")
	private double maximumStoredEnergy;
	@ApiModelProperty(required = true, example = "4000")
	private double maximumChargingRate;
	@ApiModelProperty(required = true, example = "2000")
	private double maximumDischargingRate;
	@ApiModelProperty(required = true, example = "0")
	private double storedEnergy;

	/**
	 * Solves A2.10
	 * @param dischargingRate
	 * @param chargingRate
	 * @param chargingEfficiency
	 * @return
	 */
	public double computeStoredEnergy(double dischargingRate, double chargingRate, double chargingEfficiency) {
		double tempstoredEnergy = this.storedEnergy
				- Math.min(((Math.min(dischargingRate, getMaximumDischargingRate())) / chargingEfficiency),
						storedEnergy)
				+ Math.min((Math.min(chargingRate, getMaximumChargingRate()) / chargingEfficiency),
						getMaximumPossibleCharge());
		double deltaE = tempstoredEnergy - this.storedEnergy;
		this.storedEnergy = tempstoredEnergy;
		return deltaE;
	}

	public double getStoredEnergy() {
		return storedEnergy;
	}

	public void setStoredEnergy(double storedEnergy) {
		this.storedEnergy = storedEnergy;
	}

	private double getMaximumPossibleCharge() {
		return maximumStoredEnergy - storedEnergy;
	}

	public void setMaximumStoredEnergy(double maximumStoredEnergy) {
		this.maximumStoredEnergy = maximumStoredEnergy;
	}

	public double getMaximumStoredEnergy() {
		return maximumStoredEnergy;
	}

	public void setMaximumChargingRate(double maximumChargingRate) {
		this.maximumChargingRate = maximumChargingRate;
	}

	public double getMaximumChargingRate() {
		return maximumChargingRate;
	}

	public void setMaximumDischargingRate(double maximumDischargingRate) {
		this.maximumDischargingRate = maximumDischargingRate;
	}

	public double getMaximumDischargingRate() {
		return maximumDischargingRate;
	}
}
