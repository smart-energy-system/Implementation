package com.github.smartenergysystem.simulation;

import java.util.List;
import java.util.Map;

public interface ISimulationControllerService {
	public Long addPhotovoltaicPanel(PhotovoltaicPanel photovoltaicPanel);
	public Map<Long,PhotovoltaicPanel> getPhotovoltaicPanels();
}
