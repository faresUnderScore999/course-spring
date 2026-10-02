package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAgence;

	private String nom;
	private String ville;
	private String adresse;
	private String telephone;
}
