package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.entity.Client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

	private Long idClient;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String numPermis;
	private LocalDate dateInscription;

	public static ClientDTO from(Client client) {
		if (client == null) {
			return null;
		}
		return ClientDTO.builder()
				.idClient(client.getIdClient())
				.nom(client.getNom())
				.prenom(client.getPrenom())
				.email(client.getEmail())
				.telephone(client.getTelephone())
				.numPermis(client.getNumPermis())
				.dateInscription(client.getDateInscription())
				.build();
	}
}
