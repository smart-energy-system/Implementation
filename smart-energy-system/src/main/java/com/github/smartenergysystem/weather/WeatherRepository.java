package com.github.smartenergysystem.weather;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
	
	List<WeatherForecast> findByLatitudeAndLongitudeAndTimestampLessThan(double latitude, double longitude, long timestamp);
	WeatherHistory findFirstByLatitudeAndLongitudeOrderByTimestampDesc(double latitude, double longitude);
	
}
