package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEquipement;

	private String libelle;
}
