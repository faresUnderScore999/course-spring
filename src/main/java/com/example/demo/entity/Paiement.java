package com.example.demo.entity;
import com.example.demo.enums.ModePaiement;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPaiement;

	private BigDecimal montant;
	private LocalDate datePaiement;

	@Enumerated(EnumType.STRING)
	private ModePaiement modePaiement;
}
