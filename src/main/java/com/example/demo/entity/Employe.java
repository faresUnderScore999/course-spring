package com.example.demo.entity;
import com.example.demo.enums.RoleEmploye;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEmploye;

	private String nom;
	private String prenom;

	@Enumerated(EnumType.STRING)
	private RoleEmploye role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_agence")
	@ToString.Exclude
	private Agence agence;
}
