package com.github.smartenergysystem.model;

import com.github.smartenergysystem.simulation.WindTurbine;

public class WindTurbineWithIdDTO extends WindTurbine {
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
