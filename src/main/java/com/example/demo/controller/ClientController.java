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

import com.example.demo.dto.ClientDTO;
import com.example.demo.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

	private final ClientService clientService;

	@GetMapping
	public List<ClientDTO> getAll() {
		return clientService.findAll();
	}

	@GetMapping("/{id}")
	public ClientDTO getById(@PathVariable Long id) {
		return clientService.findById(id);
	}

	@GetMapping("/email/{email}")
	public List<ClientDTO> getByEmail(@PathVariable String email) {
		return clientService.findByEmail(email);
	}

	@PostMapping
	public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(dto));
	}

	@PutMapping("/{id}")
	public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO dto) {
		return clientService.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
