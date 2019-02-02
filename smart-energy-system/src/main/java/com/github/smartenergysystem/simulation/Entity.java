package com.github.smartenergysystem.simulation;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
public abstract class Entity {
	
	@Column(name = "lat")
	@ApiModelProperty(required = true, example = "47.77066367")
	protected double latitude;
	
	@Column(name = "lon")
	@ApiModelProperty(required = true, example = "8.16947372")
	protected double longitude;

	protected String displayName;
	
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
