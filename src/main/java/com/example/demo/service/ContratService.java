package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ContratDTO;
import com.example.demo.entity.Contrat;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ContratRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratService {

	private final ContratRepository contratRepository;

	@Transactional(readOnly = true)
	public List<ContratDTO> findAll() {
		return contratRepository.findAll().stream().map(ContratDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public ContratDTO findById(Long id) {
		return ContratDTO.from(getEntity(id));
	}

	@Transactional(readOnly = true)
	public List<ContratDTO> findByValide(Boolean valide) {
		return contratRepository.findByValide(valide).stream().map(ContratDTO::from).toList();
	}

	public ContratDTO create(ContratDTO dto) {
		Contrat contrat = Contrat.builder()
				.dateSignature(dto.getDateSignature())
				.montantTotal(dto.getMontantTotal())
				.valide(dto.getValide())
				.build();
		return ContratDTO.from(contratRepository.save(contrat));
	}

	public ContratDTO update(Long id, ContratDTO dto) {
		Contrat contrat = getEntity(id);
		contrat.setDateSignature(dto.getDateSignature());
		contrat.setMontantTotal(dto.getMontantTotal());
		contrat.setValide(dto.getValide());
		return ContratDTO.from(contratRepository.save(contrat));
	}

	public void delete(Long id) {
		contratRepository.delete(getEntity(id));
	}

	private Contrat getEntity(Long id) {
		return contratRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Contrat not found with id: " + id));
	}
}
