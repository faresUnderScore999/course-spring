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
}
