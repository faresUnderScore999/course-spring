package com.example.demo.entity;
import com.example.demo.enums.StatutVehicule;
import com.example.demo.enums.CategorieVehicule;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idVehicule;

	private String immatriculation;
	private String marque;
	private String modele;

	@Enumerated(EnumType.STRING)
	private CategorieVehicule categorie;

	private BigDecimal tarifJournalier;

	@Enumerated(EnumType.STRING)
	private StatutVehicule statut;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_agence")
	@ToString.Exclude
	private Agence agence;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "vehicules_equipements",
			joinColumns = @JoinColumn(name = "id_vehicule"),
			inverseJoinColumns = @JoinColumn(name = "id_equipement"))
	@Builder.Default
	@ToString.Exclude
	private List<Equipement> equipements = new ArrayList<>();

	@OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY)
	@Builder.Default
	@ToString.Exclude
	private List<Reservation> reservations = new ArrayList<>();
}
