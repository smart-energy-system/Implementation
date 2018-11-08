package com.github.smartenergysystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.smartenergysystem.simulation.PhotovoltaicPanel;

public class TestPhotovoltaicPanel {
	
	@Test
	public void testPanel() {
		PhotovoltaicPanel panel = new PhotovoltaicPanel();
		//Our test data uses hPa so we have to convert
		panel.setMaximumPowerYield(260);
		panel.setModuleArea(1.3);
		
		assertEquals(0.2,panel.getSolarPanelYield(),0.01);
		assertEquals(0,panel.computeTemperatureLoss(25),0.01);
		assertEquals(0.005,panel.computeTemperatureLoss(26),0.0001);
		assertEquals(0,panel.computeTemperatureLoss(20),0.0001);
		assertEquals(0.14,panel.computeTotalLosses(25),0.0001);
		assertEquals(0.145,panel.computeTotalLosses(26),0.0001);
	}
	

}
