package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ContratDTO;
import com.example.demo.dto.PaiementDTO;
import com.example.demo.entity.Contrat;
import com.example.demo.entity.Paiement;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ContratRepository;
import com.example.demo.repository.PaiementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementService {

	private final PaiementRepository paiementRepository;
	private final ContratRepository contratRepository;

	@Transactional(readOnly = true)
	public List<PaiementDTO> findAll() {
		return paiementRepository.findAll().stream().map(PaiementDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public PaiementDTO findById(Long id) {
		return PaiementDTO.from(getEntity(id));
	}

	@Transactional(readOnly = true)
	public List<PaiementDTO> findByContrat(Long idContrat) {
		return paiementRepository.findByContrat_IdContrat(idContrat).stream().map(PaiementDTO::from).toList();
	}

	public PaiementDTO create(PaiementDTO dto) {
		Paiement paiement = Paiement.builder()
				.montant(dto.getMontant())
				.datePaiement(dto.getDatePaiement())
				.modePaiement(dto.getModePaiement())
				.contrat(resolveContrat(dto.getContrat()))
				.build();
		return PaiementDTO.from(paiementRepository.save(paiement));
	}

	public PaiementDTO update(Long id, PaiementDTO dto) {
		Paiement paiement = getEntity(id);
		paiement.setMontant(dto.getMontant());
		paiement.setDatePaiement(dto.getDatePaiement());
		paiement.setModePaiement(dto.getModePaiement());
		// The contrat link is only replaced when present in the payload;
		// omitting it keeps the current link untouched.
		if (dto.getContrat() != null) {
			paiement.setContrat(resolveContrat(dto.getContrat()));
		}
		return PaiementDTO.from(paiementRepository.save(paiement));
	}

	public void delete(Long id) {
		paiementRepository.delete(getEntity(id));
	}

	private Paiement getEntity(Long id) {
		return paiementRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Paiement not found with id: " + id));
	}

	private Contrat resolveContrat(ContratDTO contratDto) {
		if (contratDto == null || contratDto.getIdContrat() == null) {
			return null;
		}
		return contratRepository.findById(contratDto.getIdContrat())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Contrat not found with id: " + contratDto.getIdContrat()));
	}
}
