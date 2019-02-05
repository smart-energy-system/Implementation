package com.github.smartenergysystem.weather;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TotalWeather")
public class WeatherItem extends WeatherData {
}
