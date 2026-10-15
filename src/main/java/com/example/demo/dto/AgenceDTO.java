package com.example.demo.dto;

import com.example.demo.entity.Agence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgenceDTO {

	private Long idAgence;
	private String nom;
	private String ville;
	private String adresse;
	private String telephone;

	public static AgenceDTO from(Agence agence) {
		if (agence == null) {
			return null;
		}
		return AgenceDTO.builder()
				.idAgence(agence.getIdAgence())
				.nom(agence.getNom())
				.ville(agence.getVille())
				.adresse(agence.getAdresse())
				.telephone(agence.getTelephone())
				.build();
	}
}
