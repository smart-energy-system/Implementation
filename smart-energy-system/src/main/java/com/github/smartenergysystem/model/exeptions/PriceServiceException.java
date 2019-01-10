package com.github.smartenergysystem.model.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "There is a problem with the price service")
public class PriceServiceException extends RuntimeException {

}
