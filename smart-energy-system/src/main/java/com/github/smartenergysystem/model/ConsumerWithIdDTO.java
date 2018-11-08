package com.github.smartenergysystem.model;

import com.github.smartenergysystem.simulation.Consumer;

public class ConsumerWithIdDTO extends Consumer{
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
