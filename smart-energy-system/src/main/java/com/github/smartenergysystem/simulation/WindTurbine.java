package com.github.smartenergysystem.simulation;

import io.swagger.annotations.ApiModelProperty;

public class WindTurbine extends PositionEntity {
	public static final int CONVERT_hPA_TO_PA = 100;
	private static final double GAS_CONSTANT_WATER_VAPOR = 461.4964;
	private static final double GAS_CONSTANT_DRY_AIR = 287.0531;
	private static final double ABSOLUTE_ZERO = 273.15;
	private static final int P_EXPONENT = 8;
	private static final double E_SO = 6.1078;
	private static final double[] C = { 0.99999683, // C0
			-0.90826951E-2, // C1
			0.78736169E-4, // C2
			-0.61117958E-6, // C3
			0.43884187E-8, // C4
			-0.29883885E-10, // C5
			0.21874425E-12, // C6
			-0.17892321E-14, // C7
			0.11112018E-16, // C8
			-0.30994571E-20 }; // C9

	@ApiModelProperty(value = "The radius of the blades, measured from the center to the tips (in meter).", example = "10")
	private double bladeRadius;
	
	@ApiModelProperty(example = "1")
	private double efficiency;

	public void setBladeRadius(double newRadius) {
		this.bladeRadius = newRadius;
	}

	public double getBladeRadius() {
		return this.bladeRadius;
	}

	public void setEfficiency(double newEfficiency) {
		this.efficiency = newEfficiency;
	}

	public double getEfficiency() {
		return this.efficiency;
	}

	public double computeAreaSwept(double bladeRadius) {
		return bladeRadius * bladeRadius * Math.PI;
	}

	/**
	 * Calculates the saturated vapor pressure. It uses the Herman Wobus equation.
	 * 
	 * @param temperatureInCelsius
	 * @return the saturated vapor pressure measured in pascal
	 */
	public double computeSaturatedVaporPressure(double temperatureInCelsius) { // E_s
		double multiplicand = C[9];
		for (int i = 8; i >= 0; i--) {
			multiplicand = C[i] + (temperatureInCelsius * multiplicand);
		}
		return E_SO / Math.pow(multiplicand, P_EXPONENT) * CONVERT_hPA_TO_PA;
	}

	/**
	 * Calculates vapor pressure. Solves A2.3
	 * 
	 * @param relativeHumidity
	 * @param temperature
	 * @return
	 */
	private double computeActualVaporPressure(double relativeHumidity, double temperature) {// P_v
		return relativeHumidity * computeSaturatedVaporPressure(temperature);// * CONVERT_hPA_TO_PA;
	}

	private double computeDryAirPressure(double meassuredAirPressure, double actualVaporPressure) {
		double pressureDryAir = meassuredAirPressure - actualVaporPressure;
		return pressureDryAir;
	}

	private double computeDryAirDensity(double temperatureInKelvin, double dryPressure) {
		return (dryPressure / (GAS_CONSTANT_DRY_AIR * temperatureInKelvin));
	}

	private double computeWaterVaporDensity(double temperatureInKelvin, double vaporPressure) {
		return (vaporPressure / (GAS_CONSTANT_WATER_VAPOR * temperatureInKelvin));
	}

	/**
	 * Calculates the density of moist air. Solves A2.4.
	 * 
	 * @param temperatureInKelvin
	 * @param airPressure         in pascal
	 * @param relativeHumidity
	 * @return
	 */
	public double computeMoistAirDensity(double temperatureInCelsius, double meassuredAirPressureInPascal,
			double relativeHumidity) {
		double actualVaporPressure = computeActualVaporPressure(relativeHumidity, temperatureInCelsius);
		double dryAirPressure = computeDryAirPressure(meassuredAirPressureInPascal, actualVaporPressure);
		double dryAirDensity = computeDryAirDensity(convertCelciusToKelvin(temperatureInCelsius), dryAirPressure);

		double vaporDensity = computeWaterVaporDensity(convertCelciusToKelvin(temperatureInCelsius),
				actualVaporPressure);
		return dryAirDensity + vaporDensity;
	}

	private double convertCelciusToKelvin(double temperatureInCelsius) {
		return temperatureInCelsius + ABSOLUTE_ZERO;
	}

	// P_avail = 1/2*p*A*v^3*C_p
	/**
	 * 
	 * @param windSpeed
	 * @param meassuredAirPressureInPascal
	 * @param relativeHumidity
	 * @param temperatureInCelsius
	 * @return
	 */
	public double computeEnergyGenerated(double windSpeed, double meassuredAirPressureInPascal, double relativeHumidity,
			double temperatureInCelsius) {
		double moistAirDensity = computeMoistAirDensity(temperatureInCelsius, meassuredAirPressureInPascal,
				relativeHumidity);
		double areaSwept = computeAreaSwept(getBladeRadius());
		return 0.5 * moistAirDensity * areaSwept * Math.pow(windSpeed, 3) * getEfficiency();
	}
}