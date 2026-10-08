package com.example.demo.entity;
import com.example.demo.enums.StatutReservation;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idReservation;

	private LocalDate dateDebut;
	private LocalDate dateFin;

	@Enumerated(EnumType.STRING)
	private StatutReservation statut;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vehicule")
	@ToString.Exclude
	private Vehicule vehicule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_client")
	@ToString.Exclude
	private Client client;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_contrat")
	@ToString.Exclude
	private Contrat contrat;
}
