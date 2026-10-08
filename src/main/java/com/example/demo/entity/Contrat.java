package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contrats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contrat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContrat;

	private LocalDate dateSignature;
	private BigDecimal montantTotal;
	private Boolean valide;

	@OneToOne(mappedBy = "contrat", fetch = FetchType.LAZY)
	@ToString.Exclude
	private Reservation reservation;
}
