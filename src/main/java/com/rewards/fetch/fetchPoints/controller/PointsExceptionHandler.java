package com.rewards.fetch.fetchPoints.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rewards.fetch.fetchPoints.exception.ExceptionResponse;
import com.rewards.fetch.fetchPoints.exception.PointsException;

@ControllerAdvice
public class PointsExceptionHandler {

	@ExceptionHandler(PointsException.class)
	public ResponseEntity<ExceptionResponse> customException(PointsException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("BAD_REQUEST");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

}
