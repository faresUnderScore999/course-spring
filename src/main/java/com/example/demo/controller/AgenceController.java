package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AgenceDTO;
import com.example.demo.service.AgenceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

	private final AgenceService agenceService;

	@GetMapping
	public List<AgenceDTO> getAll() {
		return agenceService.findAll();
	}

	@GetMapping("/{id}")
	public AgenceDTO getById(@PathVariable Long id) {
		return agenceService.findById(id);
	}

	@GetMapping("/ville/{ville}")
	public List<AgenceDTO> getByVille(@PathVariable String ville) {
		return agenceService.findByVille(ville);
	}

	@PostMapping
	public ResponseEntity<AgenceDTO> create(@RequestBody AgenceDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.create(dto));
	}

	@PutMapping("/{id}")
	public AgenceDTO update(@PathVariable Long id, @RequestBody AgenceDTO dto) {
		return agenceService.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		agenceService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
