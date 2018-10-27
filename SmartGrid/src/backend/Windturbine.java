package backend;

public class Windturbine {
	private double bladeRadius;
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

	public double computeDryAirDensity(double temperatureInKelvin, double airPressure) {
		return (airPressure / (287.0531 * temperatureInKelvin));
	}

	public double computeWaterVaporDensity(double temperatureInKelvin, double airPressure) {
		return (airPressure / (461.4964 * temperatureInKelvin));
	}

	// t is the temperature in degrees Celsius
	public double computeSaturatedVaporPressure(double t) {
		return 6.1078 / (0.99999683 + t * (-0.90826951 * Math.pow(10, -2) + t * (0.78736169 * Math.pow(10, -4)
				+ t * (-0.61117958 * Math.pow(10, -6) + t * (0.43884187 * Math.pow(10, -8) + t * (-0.29883885
						* Math.pow(10, -10)
						+ t * (0.21874425 * Math.pow(10, -12) + t * (-0.17892321 * Math.pow(10, -14)
								+ t * (0.11112018 * Math.pow(10, -16) + t * (-0.30994571 * Math.pow(10, -19)))))))))));
	}

	public double computeActualVaporPressure(double relativeHumidity, double temperature) {
		return relativeHumidity * computeSaturatedVaporPressure(temperature);
	}

	public double computeMoistAirDensity(double temperatureInKelvin, double airPressure, double relativeHumidity) {
		double pv = computeSaturatedVaporPressure(temperatureInKelvin - 273.15);
		return computeDryAirDensity(temperatureInKelvin, airPressure - pv)
				* computeWaterVaporDensity(temperatureInKelvin, pv);
	}

	// P_avail = 1/2*p*A*v^3*C_p
	public double computePowerOutput(double windSpeed, double airPressure, double relativeHumidity,
			double temperatureInKelvin) {
		return (1 / 2) * computeMoistAirDensity(temperatureInKelvin, airPressure, relativeHumidity)
				* computeAreaSwept(getBladeRadius()) * Math.pow(windSpeed, 3) * getEfficiency();
	}
}