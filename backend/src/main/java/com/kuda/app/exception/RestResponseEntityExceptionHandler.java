package com.kuda.app.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ KudaException.class })
	public ResponseEntity<Object> handleKudaException(KudaException ex, WebRequest request) {

		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("id", ex.getId());
		errorResponse.put("resource", ex.getResource());
		errorResponse.put("message", ex.getMessage());
		errorResponse.put("timestamp", Instant.now().toEpochMilli());
		return new ResponseEntity<>(errorResponse, new HttpHeaders(),
				ex.getStatus() != null ? ex.getStatus() : HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {

		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("message", ex.getMessage());
		errorResponse.put("timestamp", Instant.now().toEpochMilli());
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
