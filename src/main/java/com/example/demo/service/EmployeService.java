package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.EmployeDTO;
import com.example.demo.entity.Agence;
import com.example.demo.entity.Employe;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AgenceRepository;
import com.example.demo.repository.EmployeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;

    @Transactional(readOnly = true)
    public List<EmployeDTO> findAll() {
        return employeRepository.findAll().stream()
                .map(EmployeDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmployeDTO findById(Long id) {
        return EmployeDTO.from(getEntity(id));
    }

    public EmployeDTO create(EmployeDTO dto) {
        Employe employe = Employe.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .role(dto.getRole())
                .agence(dto.getIdAgence() != null ? getAgence(dto.getIdAgence()) : null)
                .build();
        return EmployeDTO.from(employeRepository.save(employe));
    }

    public EmployeDTO update(Long id, EmployeDTO dto) {
        Employe employe = getEntity(id);
        employe.setNom(dto.getNom());
        employe.setPrenom(dto.getPrenom());
        employe.setRole(dto.getRole());
        employe.setAgence(dto.getIdAgence() != null ? getAgence(dto.getIdAgence()) : null);
        return EmployeDTO.from(employeRepository.save(employe));
    }

    public void delete(Long id) {
        employeRepository.delete(getEntity(id));
    }

    /* ========================================================================
       Méthodes demandées : mutation de l'agence de l'employé
       ======================================================================== */

    public EmployeDTO desaffecterEmploye(Long idEmploye) {
        Employe employe = getEntity(idEmploye);
        employe.setAgence(null);
        return EmployeDTO.from(employeRepository.save(employe));
    }

    public EmployeDTO affecterEmploye(Long idEmploye, Long idAgence) {
        Employe employe = getEntity(idEmploye);
        Agence agence = getAgence(idAgence);
        employe.setAgence(agence);
        return EmployeDTO.from(employeRepository.save(employe));
    }

    /* ========================================================================
       Helpers privés
       ======================================================================== */

    private Employe getEntity(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe not found with id: " + id));
    }

    private Agence getAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence not found with id: " + id));
    }
}

