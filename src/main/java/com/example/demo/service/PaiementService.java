package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.entity.Paiement;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PaiementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementService {

	private final PaiementRepository paiementRepository;

	@Transactional(readOnly = true)
	public List<PaiementDTO> findAll() {
		return paiementRepository.findAll().stream().map(PaiementDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public PaiementDTO findById(Long id) {
		return PaiementDTO.from(getEntity(id));
	}

	public PaiementDTO create(PaiementDTO dto) {
		Paiement paiement = Paiement.builder()
				.montant(dto.getMontant())
				.datePaiement(dto.getDatePaiement())
				.modePaiement(dto.getModePaiement())
				.build();
		return PaiementDTO.from(paiementRepository.save(paiement));
	}

	public PaiementDTO update(Long id, PaiementDTO dto) {
		Paiement paiement = getEntity(id);
		paiement.setMontant(dto.getMontant());
		paiement.setDatePaiement(dto.getDatePaiement());
		paiement.setModePaiement(dto.getModePaiement());
		return PaiementDTO.from(paiementRepository.save(paiement));
	}

	public void delete(Long id) {
		paiementRepository.delete(getEntity(id));
	}

	private Paiement getEntity(Long id) {
		return paiementRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Paiement not found with id: " + id));
	}
}
