package com.github.smartenergysystem.model;

import com.github.smartenergysystem.simulation.Battery;

public class BatteryWithIdDTO extends Battery{
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
