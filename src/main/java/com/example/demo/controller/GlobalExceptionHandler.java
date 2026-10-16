package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.demo.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
		return build(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return build(HttpStatus.BAD_REQUEST,
				"Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'");
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> handleUnreadableBody(HttpMessageNotReadableException ex) {
		return build(HttpStatus.BAD_REQUEST, "Malformed or unreadable request body");
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidDataAccess(InvalidDataAccessApiUsageException ex) {
		// e.g. "The given id must not be null" when a body-id/path-id is missing
		return build(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
		return build(HttpStatus.CONFLICT,
				"Operation violates data integrity (record may be referenced by other records)");
	}

	private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(Map.of(
				"timestamp", LocalDateTime.now().toString(),
				"status", status.value(),
				"error", status.getReasonPhrase(),
				"message", message == null ? "" : message));
	}
}
