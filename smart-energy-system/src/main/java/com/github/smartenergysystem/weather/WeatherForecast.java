package com.github.smartenergysystem.weather;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WeatherForecast")
public class WeatherForecast extends WeatherData {

}
