package com.github.smartenergysystem.price.collector.priceCollector;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "At least one value could not be obtained and is not in the database")
public class NotInDatebaseException extends RuntimeException {
}
