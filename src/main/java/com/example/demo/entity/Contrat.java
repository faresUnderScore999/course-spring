package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	@ToString.Exclude
	private List<Paiement> paiements = new ArrayList<>();
}
