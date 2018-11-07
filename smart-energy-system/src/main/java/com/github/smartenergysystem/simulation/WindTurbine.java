package com.github.smartenergysystem.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.ApiModelProperty;

public class WindTurbine extends PositionEntity {
	
	Logger logger = LoggerFactory.getLogger(WindTurbine.class);
	
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

	/**
	 * Calculates the area swept for a given bladeradius
	 * 
	 * @param bladeRadius in meters
	 * @return areaswept in squaremeters
	 */
	public double computeAreaSwept(double bladeRadius) {
		return bladeRadius * bladeRadius * Math.PI;
	}

	/**
	 * Calculates the saturated vapor pressure. It uses the Herman Wobus equation.
	 * 
	 * @param temperatureInCelsius in degrees Celsius
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
	 * @param relativeHumidity as double for example: 0.13
	 * @param temperature in degrees Celsius
	 * @return actual vapor pressure in pascal
	 */
	private double computeActualVaporPressure(double relativeHumidity, double temperature) {// P_v
		return relativeHumidity * computeSaturatedVaporPressure(temperature);// * CONVERT_hPA_TO_PA;
	}

	/**
	 * 
	 * @param meassuredAirPressure in pascal
	 * @param actualVaporPressure in pascal
	 * @return pressure of the dry component of air in pascal
	 */
	private double computeDryAirPressure(double meassuredAirPressure, double actualVaporPressure) {
		double pressureDryAir = meassuredAirPressure - actualVaporPressure;
		return pressureDryAir;
	}

	/**
	 * 
	 * @param temperatureInKelvin in degrees Kelvin
	 * @param dryPressure in pascal
	 * @return density of dry air in kg/(m^3)
	 */
	private double computeDryAirDensity(double temperatureInKelvin, double dryPressure) {
		return (dryPressure / (GAS_CONSTANT_DRY_AIR * temperatureInKelvin));
	}

	/**
	 * 
	 * @param temperatureInKelvin in degrees Kelvin
	 * @param vaporPressure in pascal
	 * @return density of water vapor in kg/(m^3)
	 */
	private double computeWaterVaporDensity(double temperatureInKelvin, double vaporPressure) {
		return (vaporPressure / (GAS_CONSTANT_WATER_VAPOR * temperatureInKelvin));
	}

	/**
	 * Calculates the density of moist air. Solves A2.4.
	 * 
	 * @param temperatureInKelvin in degrees Kelvin
	 * @param airPressure in pascal
	 * @param relativeHumidity in percentage as a double for example 10%: 0.1
	 * @return moist air density in kg/(m^3)
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
		logger.debug("Calculation energy for wind turbine with: windSpeed:"+windSpeed+ " meassuredAirPressureInPascal:"+meassuredAirPressureInPascal + " relativeHumidity:"+ relativeHumidity +
				" temperatureInCelsius:"+temperatureInCelsius);
		double moistAirDensity = computeMoistAirDensity(temperatureInCelsius, meassuredAirPressureInPascal,
				relativeHumidity);
		double areaSwept = computeAreaSwept(getBladeRadius());
		return 0.5 * moistAirDensity * areaSwept * Math.pow(windSpeed, 3) * getEfficiency();
	}
}