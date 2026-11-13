package com.example.demo.dto;

import com.example.demo.entity.Employe;
import com.example.demo.enums.RoleEmploye;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeDTO {

    private Long idEmploye;
    private String nom;
    private String prenom;
    private RoleEmploye role;
    private Long idAgence;

    public static EmployeDTO from(Employe employe) {
        if (employe == null) {
            return null;
        }
        return EmployeDTO.builder()
                .idEmploye(employe.getIdEmploye())
                .nom(employe.getNom())
                .prenom(employe.getPrenom())
                .role(employe.getRole())
                .idAgence(employe.getAgence() != null ? employe.getAgence().getIdAgence() : null)
                .build();
    }
}
