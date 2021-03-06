package com.github.smartenergysystem.weather;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import com.github.smartenergysystem.simulation.PositionEntity;
import com.github.smartenergysystem.simulation.WindTurbine;

import java.util.Date;

@MappedSuperclass
public class WeatherData extends PositionEntity {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "temp")
	private double temperature;
	
	@Column(name = "windspeed")
	private double windSpeed;
	
	@Column(name = "airpressure")
	private Double airPressure;
	
	@Column(name = "humidity")
	private double humidity;
	
	@Column(name = "timestamp")
	private long timestamp;
	
	@Column(name = "ghi")
	private double globalHorizontalSolarIrradiance;

	
	public double getGlobalHorizontalSolarIrradiance() {
		return globalHorizontalSolarIrradiance;
	}
	public void setGlobalHorizontalSolarIrradiance(double globalHorizontalSolarIrradiance) {
		this.globalHorizontalSolarIrradiance = globalHorizontalSolarIrradiance;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	/**
	 * 
	 * @return the air pressure in hpa
	 */
	public double getAirPressure() {
		if(airPressure == null){
			return 1003.0;
		}
		return airPressure;
	}
	
	public double getAirPressureInPascal() {
		return getAirPressure() * WindTurbine.CONVERT_hPA_TO_PA;
	}

	public void setAirPressure(Double airPressure) {
		this.airPressure = airPressure;
	}
	
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "WeatherForecast [id=" + id + ", temperature=" + temperature + ", windSpeed=" + windSpeed
				+ ", airPressure=" + airPressure + ", humidity=" + humidity + ", timestamp(as Date)=" + new Date(timestamp) + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}
	
	
	

}
