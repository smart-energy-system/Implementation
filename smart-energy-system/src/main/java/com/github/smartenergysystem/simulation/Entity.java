package com.github.smartenergysystem.simulation;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
public abstract class Entity extends PositionEntity{


	protected String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
