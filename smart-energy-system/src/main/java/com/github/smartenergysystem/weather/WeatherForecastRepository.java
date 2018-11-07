package com.github.smartenergysystem.weather;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherForecastRepository extends JpaRepository<WeatherForecast, Long> {
	
	List<WeatherForecast> findByLatitudeAndLongitude(double latitude, double longitude);
	
	
}
