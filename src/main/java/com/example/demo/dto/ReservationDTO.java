package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.entity.Reservation;
import com.example.demo.enums.StatutReservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Nested references are one-directional summaries so the JSON graph always
 * terminates (no back-references to Reservation are serialized).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

	private Long idReservation;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private StatutReservation statut;
	private VehiculeDTO vehicule;
	private ClientDTO client;
	private ContratDTO contrat;

	public static ReservationDTO from(Reservation reservation) {
		if (reservation == null) {
			return null;
		}
		return ReservationDTO.builder()
				.idReservation(reservation.getIdReservation())
				.dateDebut(reservation.getDateDebut())
				.dateFin(reservation.getDateFin())
				.statut(reservation.getStatut())
				.vehicule(VehiculeDTO.from(reservation.getVehicule()))
				.client(ClientDTO.from(reservation.getClient()))
				.contrat(ContratDTO.from(reservation.getContrat()))
				.build();
	}
}
