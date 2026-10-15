package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AgenceDTO;
import com.example.demo.dto.VehiculeDTO;
import com.example.demo.entity.Agence;
import com.example.demo.entity.Vehicule;
import com.example.demo.enums.StatutVehicule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AgenceRepository;
import com.example.demo.repository.VehiculeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeService {

	private final VehiculeRepository vehiculeRepository;
	private final AgenceRepository agenceRepository;

	@Transactional(readOnly = true)
	public List<VehiculeDTO> findAll() {
		return vehiculeRepository.findAll().stream().map(VehiculeDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public VehiculeDTO findById(Long id) {
		return VehiculeDTO.from(getEntity(id));
	}

	@Transactional(readOnly = true)
	public List<VehiculeDTO> findByStatut(StatutVehicule statut) {
		return vehiculeRepository.findByStatut(statut).stream().map(VehiculeDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public List<VehiculeDTO> findByAgence(Long idAgence) {
		return vehiculeRepository.findByAgence_IdAgence(idAgence).stream().map(VehiculeDTO::from).toList();
	}

	public VehiculeDTO create(VehiculeDTO dto) {
		Vehicule vehicule = Vehicule.builder()
				.immatriculation(dto.getImmatriculation())
				.marque(dto.getMarque())
				.modele(dto.getModele())
				.categorie(dto.getCategorie())
				.tarifJournalier(dto.getTarifJournalier())
				.statut(dto.getStatut())
				.agence(resolveAgence(dto.getAgence()))
				.build();
		return VehiculeDTO.from(vehiculeRepository.save(vehicule));
	}

	public VehiculeDTO update(Long id, VehiculeDTO dto) {
		Vehicule vehicule = getEntity(id);
		vehicule.setImmatriculation(dto.getImmatriculation());
		vehicule.setMarque(dto.getMarque());
		vehicule.setModele(dto.getModele());
		vehicule.setCategorie(dto.getCategorie());
		vehicule.setTarifJournalier(dto.getTarifJournalier());
		vehicule.setStatut(dto.getStatut());
		// Only replace the agency when the payload carries one; omitting "agence"
		// keeps the current link untouched.
		if (dto.getAgence() != null) {
			vehicule.setAgence(resolveAgence(dto.getAgence()));
		}
		return VehiculeDTO.from(vehiculeRepository.save(vehicule));
	}

	public void delete(Long id) {
		vehiculeRepository.delete(getEntity(id));
	}

	private Vehicule getEntity(Long id) {
		return vehiculeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicule not found with id: " + id));
	}

	private Agence resolveAgence(AgenceDTO agenceDto) {
		if (agenceDto == null || agenceDto.getIdAgence() == null) {
			return null;
		}
		return agenceRepository.findById(agenceDto.getIdAgence())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Agence not found with id: " + agenceDto.getIdAgence()));
	}
}
