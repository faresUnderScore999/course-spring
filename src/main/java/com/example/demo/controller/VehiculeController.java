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

import com.example.demo.dto.VehiculeDTO;
import com.example.demo.enums.StatutVehicule;
import com.example.demo.service.VehiculeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

	private final VehiculeService vehiculeService;

	@GetMapping
	public List<VehiculeDTO> getAll() {
		return vehiculeService.findAll();
	}

	@GetMapping("/{id}")
	public VehiculeDTO getById(@PathVariable Long id) {
		return vehiculeService.findById(id);
	}

	@GetMapping("/statut/{statut}")
	public List<VehiculeDTO> getByStatut(@PathVariable StatutVehicule statut) {
		return vehiculeService.findByStatut(statut);
	}

	@GetMapping("/agence/{idAgence}")
	public List<VehiculeDTO> getByAgence(@PathVariable Long idAgence) {
		return vehiculeService.findByAgence(idAgence);
	}

	@PostMapping
	public ResponseEntity<VehiculeDTO> create(@RequestBody VehiculeDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.create(dto));
	}

	@PutMapping("/{id}")
	public VehiculeDTO update(@PathVariable Long id, @RequestBody VehiculeDTO dto) {
		return vehiculeService.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		vehiculeService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
