package com.example.demo.entity;
import com.example.demo.enums.StatutVehicule;
import com.example.demo.enums.CategorieVehicule;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

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
}
