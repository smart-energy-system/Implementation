package com.github.smartenergysystem.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherCompleteRepository extends JpaRepository<WeatherItem, Long> {

    List<WeatherItem> findByLatitudeAndLongitudeOrderByTimestampDesc(double latitude, double longitude);
}
