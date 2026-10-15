package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.demo.entity.Contrat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Scalar-only view of a Contrat (the back-reference to Reservation is
 * deliberately omitted to avoid JSON cycles).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratDTO {

	private Long idContrat;
	private LocalDate dateSignature;
	private BigDecimal montantTotal;
	private Boolean valide;

	public static ContratDTO from(Contrat contrat) {
		if (contrat == null) {
			return null;
		}
		return ContratDTO.builder()
				.idContrat(contrat.getIdContrat())
				.dateSignature(contrat.getDateSignature())
				.montantTotal(contrat.getMontantTotal())
				.valide(contrat.getValide())
				.build();
	}
}
