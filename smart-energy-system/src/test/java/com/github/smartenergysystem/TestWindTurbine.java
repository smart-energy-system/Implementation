package com.github.smartenergysystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.smartenergysystem.simulation.WindTurbine;

public class TestWindTurbine {
	
	@Test
	public void testSaturatedVapurePressure() {
		WindTurbine windTurbine = new WindTurbine();
		//Our test data uses hPa so we have to convert
		assertEquals(31.67,windTurbine.computeSaturatedVaporPressure(25)/WindTurbine.CONVERT_hPA_TO_PA,0.01);
		assertEquals(6.108,windTurbine.computeSaturatedVaporPressure(0)/WindTurbine.CONVERT_hPA_TO_PA,0.01);
		assertEquals(4.215,windTurbine.computeSaturatedVaporPressure(-5)/WindTurbine.CONVERT_hPA_TO_PA,0.01);
		assertEquals(17.044,windTurbine.computeSaturatedVaporPressure(15)/WindTurbine.CONVERT_hPA_TO_PA,0.01);
	}
	
	@Test
	public void testMoistAirDensity() {
		WindTurbine windTurbine = new WindTurbine();
		//Used this https://www.omnicalculator.com/physics/air-density
		assertEquals(1.2334,windTurbine.computeMoistAirDensity(14, 1020 * WindTurbine.CONVERT_hPA_TO_PA, 0.74),0.01);
		assertEquals(0.09685,windTurbine.computeMoistAirDensity(30, 100 * WindTurbine.CONVERT_hPA_TO_PA, 0.99),0.01);
		assertEquals(0.0012704,windTurbine.computeMoistAirDensity(1, 1 * WindTurbine.CONVERT_hPA_TO_PA, 0.01),0.01);
	}

}
