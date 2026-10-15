package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.demo.entity.Paiement;
import com.example.demo.enums.ModePaiement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDTO {

	private Long idPaiement;
	private BigDecimal montant;
	private LocalDate datePaiement;
	private ModePaiement modePaiement;

	public static PaiementDTO from(Paiement paiement) {
		if (paiement == null) {
			return null;
		}
		return PaiementDTO.builder()
				.idPaiement(paiement.getIdPaiement())
				.montant(paiement.getMontant())
				.datePaiement(paiement.getDatePaiement())
				.modePaiement(paiement.getModePaiement())
				.build();
	}
}
