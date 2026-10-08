package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
	@Builder.Default
	@ToString.Exclude
	private List<Employe> employes = new ArrayList<>();

	@OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
	@Builder.Default
	@ToString.Exclude
	private List<Vehicule> vehicules = new ArrayList<>();
}
