package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.EmployeDTO;
import com.example.demo.service.EmployeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    /* ---------- CRUD classique ---------- */

    @GetMapping
    public List<EmployeDTO> getAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeDTO getById(@PathVariable Long id) {
        return employeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<EmployeDTO> create(@RequestBody EmployeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.create(dto));
    }

    @PutMapping("/{id}")
    public EmployeDTO update(@PathVariable Long id, @RequestBody EmployeDTO dto) {
        return employeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /* ---------- Mutation d'agence (votre scénario) ---------- */

    @PutMapping("/{idEmploye}/desaffecter")
    public ResponseEntity<EmployeDTO> desaffecter(@PathVariable Long idEmploye) {
        return ResponseEntity.ok(employeService.desaffecterEmploye(idEmploye));
    }

    @PutMapping("/{idEmploye}/affecter/{idAgence}")
    public ResponseEntity<EmployeDTO> affecter(@PathVariable Long idEmploye, @PathVariable Long idAgence) {
        return ResponseEntity.ok(employeService.affecterEmploye(idEmploye, idAgence));
    }
}

