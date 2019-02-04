package com.github.smartenergysystem.model;

import io.swagger.annotations.ApiModelProperty;

public class ChargeProcessInput {
	
	@ApiModelProperty(required = true, example = "0")
	double dischargingRate;
	@ApiModelProperty(required = true, example = "1")
	double chargingRate;
	@ApiModelProperty(required = true, example = "0.5")
	double chargingEfficiency;
	
	public double getDischargingRate() {
		return dischargingRate;
	}
	public void setDischargingRate(double dischargingRate) {
		this.dischargingRate = dischargingRate;
	}
	public double getChargingRate() {
		return chargingRate;
	}
	public void setChargingRate(double chargingRate) {
		this.chargingRate = chargingRate;
	}
	public double getChargingEfficiency() {
		return chargingEfficiency;
	}
	public void setChargingEfficiency(double chargingEfficiency) {
		this.chargingEfficiency = chargingEfficiency;
	}

	
	
}
