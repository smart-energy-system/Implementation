package com.github.smartenergysystem.model.exeptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such id")
public class IdNotFoundException extends RuntimeException {

}
