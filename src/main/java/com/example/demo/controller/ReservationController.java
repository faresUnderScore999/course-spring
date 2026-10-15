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

import com.example.demo.dto.ReservationDTO;
import com.example.demo.enums.StatutReservation;
import com.example.demo.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@GetMapping
	public List<ReservationDTO> getAll() {
		return reservationService.findAll();
	}

	@GetMapping("/{id}")
	public ReservationDTO getById(@PathVariable Long id) {
		return reservationService.findById(id);
	}

	@GetMapping("/statut/{statut}")
	public List<ReservationDTO> getByStatut(@PathVariable StatutReservation statut) {
		return reservationService.findByStatut(statut);
	}

	@GetMapping("/client/{idClient}")
	public List<ReservationDTO> getByClient(@PathVariable Long idClient) {
		return reservationService.findByClient(idClient);
	}

	@GetMapping("/vehicule/{idVehicule}")
	public List<ReservationDTO> getByVehicule(@PathVariable Long idVehicule) {
		return reservationService.findByVehicule(idVehicule);
	}

	@PostMapping
	public ResponseEntity<ReservationDTO> create(@RequestBody ReservationDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(dto));
	}

	@PutMapping("/{id}")
	public ReservationDTO update(@PathVariable Long id, @RequestBody ReservationDTO dto) {
		return reservationService.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		reservationService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
