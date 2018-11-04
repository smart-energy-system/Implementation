package com.github.smartenergysystem.simulation;

import io.swagger.annotations.ApiModelProperty;

public abstract class Supplier {
	
	@ApiModelProperty(required = true, example = "48.77066367")
	protected double latitude;
	@ApiModelProperty(required = true, example = "9.16947372")
	protected double longitude;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
