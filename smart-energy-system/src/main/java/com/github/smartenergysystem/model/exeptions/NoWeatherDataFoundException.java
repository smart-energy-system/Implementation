package com.github.smartenergysystem.model.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no weather data available.")
public class NoWeatherDataFoundException extends RuntimeException {

}
