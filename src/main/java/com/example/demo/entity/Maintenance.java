package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "maintenances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Maintenance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMaintenance;

	private LocalDate dateDebut;
	private LocalDate dateFin;
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_vehicule")
	@ToString.Exclude
	private Vehicule vehicule;
}
