package com.github.smartenergysystem.weather;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.smartenergysystem.simulation.PositionEntity;

@Entity
@Table(name = "WeatherForecast")
public class WeatherForecast extends PositionEntity {
	
	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "temp")
	private double temperature;
	
	@Column(name = "windspeed")
	private double windSpeed;
	
	@Column(name = "airpressure")
	private double airPressure;
	
	@Column(name = "humidity")
	private double humidity;
	
	@Column(name = "timestamp")
	private long timestamp;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public double getAirPressure() {
		return airPressure;
	}
	public void setAirPressure(double airPressure) {
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
				+ ", airPressure=" + airPressure + ", humidity=" + humidity + ", timestamp=" + timestamp + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}
	
	
	

}
