package com.github.smartenergysystem.weather;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Weather")
public class WeatherHistory extends WeatherData{

}
