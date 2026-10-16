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

import com.example.demo.dto.ContratDTO;
import com.example.demo.entity.Contrat;
import com.example.demo.service.ContratService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

	private final ContratService contratService;

	@GetMapping
	public List<ContratDTO> getAll() {
		return contratService.findAll();
	}

	@GetMapping("/{id}")
	public ContratDTO getById(@PathVariable Long id) {
		return contratService.findById(id);
	}

	@GetMapping("/valide/{valide}")
	public List<ContratDTO> getByValide(@PathVariable Boolean valide) {
		return contratService.findByValide(valide);
	}

	@PostMapping
	public ResponseEntity<ContratDTO> create(@RequestBody ContratDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(contratService.create(dto));
	}

	@PutMapping("/{id}")
	public ContratDTO update(@PathVariable Long id, @RequestBody ContratDTO dto) {
		return contratService.update(id, dto);
	}
@PutMapping
public ContratDTO update(@RequestBody ContratDTO dto) {
    return contratService.update(dto.getIdContrat(), dto);
}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		contratService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
