package com.example.demo.dto;

import java.math.BigDecimal;

import com.example.demo.entity.Vehicule;
import com.example.demo.enums.CategorieVehicule;
import com.example.demo.enums.StatutVehicule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeDTO {

	private Long idVehicule;
	private String immatriculation;
	private String marque;
	private String modele;
	private CategorieVehicule categorie;
	private BigDecimal tarifJournalier;
	private StatutVehicule statut;
	private AgenceDTO agence;

	public static VehiculeDTO from(Vehicule vehicule) {
		if (vehicule == null) {
			return null;
		}
		return VehiculeDTO.builder()
				.idVehicule(vehicule.getIdVehicule())
				.immatriculation(vehicule.getImmatriculation())
				.marque(vehicule.getMarque())
				.modele(vehicule.getModele())
				.categorie(vehicule.getCategorie())
				.tarifJournalier(vehicule.getTarifJournalier())
				.statut(vehicule.getStatut())
				.agence(AgenceDTO.from(vehicule.getAgence()))
				.build();
	}
}
