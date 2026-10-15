package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ContratDTO;
import com.example.demo.dto.ReservationDTO;
import com.example.demo.dto.VehiculeDTO;
import com.example.demo.entity.Client;
import com.example.demo.entity.Contrat;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Vehicule;
import com.example.demo.enums.StatutReservation;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.VehiculeRepository;

import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final VehiculeRepository vehiculeRepository;
	private final ClientRepository clientRepository;
	private final EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<ReservationDTO> findAll() {
		return reservationRepository.findAll().stream().map(ReservationDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public ReservationDTO findById(Long id) {
		return ReservationDTO.from(getEntity(id));
	}

	@Transactional(readOnly = true)
	public List<ReservationDTO> findByStatut(StatutReservation statut) {
		return reservationRepository.findByStatut(statut).stream().map(ReservationDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public List<ReservationDTO> findByClient(Long idClient) {
		return reservationRepository.findByClient_IdClient(idClient).stream().map(ReservationDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public List<ReservationDTO> findByVehicule(Long idVehicule) {
		return reservationRepository.findByVehicule_IdVehicule(idVehicule).stream()
				.map(ReservationDTO::from).toList();
	}

	public ReservationDTO create(ReservationDTO dto) {
		Reservation reservation = Reservation.builder()
				.dateDebut(dto.getDateDebut())
				.dateFin(dto.getDateFin())
				.statut(dto.getStatut())
				.vehicule(resolveVehicule(dto.getVehicule()))
				.client(resolveClient(dto.getClient()))
				.contrat(resolveContrat(dto.getContrat()))
				.build();
		return ReservationDTO.from(reservationRepository.save(reservation));
	}

	public ReservationDTO update(Long id, ReservationDTO dto) {
		Reservation reservation = getEntity(id);
		reservation.setDateDebut(dto.getDateDebut());
		reservation.setDateFin(dto.getDateFin());
		reservation.setStatut(dto.getStatut());
		// Nested references are only replaced when present in the payload;
		// omitting them keeps the current links untouched.
		if (dto.getVehicule() != null) {
			reservation.setVehicule(resolveVehicule(dto.getVehicule()));
		}
		if (dto.getClient() != null) {
			reservation.setClient(resolveClient(dto.getClient()));
		}
		if (dto.getContrat() != null) {
			reservation.setContrat(resolveContrat(dto.getContrat()));
		}
		return ReservationDTO.from(reservationRepository.save(reservation));
	}

	public void delete(Long id) {
		reservationRepository.delete(getEntity(id));
	}

	private Reservation getEntity(Long id) {
		return reservationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
	}

	private Vehicule resolveVehicule(VehiculeDTO vehiculeDto) {
		if (vehiculeDto == null || vehiculeDto.getIdVehicule() == null) {
			return null;
		}
		return vehiculeRepository.findById(vehiculeDto.getIdVehicule())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Vehicule not found with id: " + vehiculeDto.getIdVehicule()));
	}

	private Client resolveClient(ClientDTO clientDto) {
		if (clientDto == null || clientDto.getIdClient() == null) {
			return null;
		}
		return clientRepository.findById(clientDto.getIdClient())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Client not found with id: " + clientDto.getIdClient()));
	}

	private Contrat resolveContrat(ContratDTO contratDto) {
		if (contratDto == null || contratDto.getIdContrat() == null) {
			return null;
		}
		Contrat contrat = entityManager.find(Contrat.class, contratDto.getIdContrat());
		if (contrat == null) {
			throw new ResourceNotFoundException("Contrat not found with id: " + contratDto.getIdContrat());
		}
		return contrat;
	}
}
