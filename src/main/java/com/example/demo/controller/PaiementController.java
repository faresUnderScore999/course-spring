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

import com.example.demo.dto.PaiementDTO;
import com.example.demo.service.PaiementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

	private final PaiementService paiementService;

	@GetMapping
	public List<PaiementDTO> getAll() {
		return paiementService.findAll();
	}

	@GetMapping("/{id}")
	public PaiementDTO getById(@PathVariable Long id) {
		return paiementService.findById(id);
	}

	@GetMapping("/contrat/{idContrat}")
	public List<PaiementDTO> getByContrat(@PathVariable Long idContrat) {
		return paiementService.findByContrat(idContrat);
	}

	@PostMapping
	public ResponseEntity<PaiementDTO> create(@RequestBody PaiementDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(paiementService.create(dto));
	}

	@PutMapping("/{id}")
	public PaiementDTO update(@PathVariable Long id, @RequestBody PaiementDTO dto) {
		return paiementService.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		paiementService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
