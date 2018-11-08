package com.github.smartenergysystem.simulation;

import io.swagger.annotations.ApiModelProperty;

public class Battery {

	private Long id;
	
	@ApiModelProperty(required = true, example = "10")
	private double maximumStoredEnergy;
	@ApiModelProperty(required = true, example = "5")
	private double maximumChargingRate;
	@ApiModelProperty(required = true, example = "2")
	private double maximumDischargingRate;
	@ApiModelProperty(required = true, example = "0")
	private double storedEnergy;

	public double computeStoredEnergy(double dischargingRate, double chargingRate, double chargingEfficiency) {
		double tempstoredEnergy = this.storedEnergy
				- Math.min(((Math.min(dischargingRate, getMaximumDischargingRate())) / chargingEfficiency),
						storedEnergy)
				+ Math.min((Math.min(chargingRate, getMaximumChargingRate()) / chargingEfficiency),
						getMaximumPossibleCharge());
		double deltaE = tempstoredEnergy - this.storedEnergy;
		return deltaE;
	}

	@ApiModelProperty(hidden=true)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public double getStoredEnergy() {
		return storedEnergy;
	}


	public void setStoredEnergy(double storedEnergy) {
		this.storedEnergy = storedEnergy;
	}

	public double getMaximumPossibleCharge() {
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
