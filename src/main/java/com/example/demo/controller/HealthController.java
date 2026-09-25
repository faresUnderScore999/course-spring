package com.example.demo.controller;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	private final JdbcTemplate jdbcTemplate;

	public HealthController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@GetMapping("/api/health")
	public Map<String, String> health() {
		return Map.of(
				"status", "UP",
				"database", isDatabaseUp() ? "UP" : "DOWN");
	}

	private boolean isDatabaseUp() {
		try {
			Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
			return result != null && result == 1;
		} catch (Exception e) {
			return false;
		}
	}

}