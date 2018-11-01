package com.github.smartenergysystem.model;

import com.github.smartenergysystem.simulation.PhotovoltaicPanel;

public class PhotovoltaicPanelWithIdDTO extends PhotovoltaicPanel {
	
	public PhotovoltaicPanelWithIdDTO() {}

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
