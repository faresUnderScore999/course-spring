package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AgenceDTO;
import com.example.demo.entity.Agence;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AgenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceService {

	private final AgenceRepository agenceRepository;

	@Transactional(readOnly = true)
	public List<AgenceDTO> findAll() {
		return agenceRepository.findAll().stream().map(AgenceDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public AgenceDTO findById(Long id) {
		return AgenceDTO.from(getEntity(id));
	}

	@Transactional(readOnly = true)
	public List<AgenceDTO> findByVille(String ville) {
		return agenceRepository.findByVille(ville).stream().map(AgenceDTO::from).toList();
	}

	public AgenceDTO create(AgenceDTO dto) {
		Agence agence = Agence.builder()
				.nom(dto.getNom())
				.ville(dto.getVille())
				.adresse(dto.getAdresse())
				.telephone(dto.getTelephone())
				.build();
		return AgenceDTO.from(agenceRepository.save(agence));
	}

	public AgenceDTO update(Long id, AgenceDTO dto) {
		Agence agence = getEntity(id);
		agence.setNom(dto.getNom());
		agence.setVille(dto.getVille());
		agence.setAdresse(dto.getAdresse());
		agence.setTelephone(dto.getTelephone());
		return AgenceDTO.from(agenceRepository.save(agence));
	}

	public void delete(Long id) {
		agenceRepository.delete(getEntity(id));
	}

	private Agence getEntity(Long id) {
		return agenceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Agence not found with id: " + id));
	}
}
