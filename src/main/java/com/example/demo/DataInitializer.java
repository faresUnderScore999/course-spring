package com.example.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.demo.entity.Agence;
import com.example.demo.entity.Employe;
import com.example.demo.enums.RoleEmploye;
import com.example.demo.repository.AgenceRepository;
import com.example.demo.repository.EmployeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Seeds the database with the two agencies (Tunis, Sfax) and an employee
 * initially assigned to Sfax, so the mutation scenario exists out of the box.
 * Only active in the "seed" profile.
 */
@Configuration
@Profile("seed")
@RequiredArgsConstructor
public class DataInitializer {

    private final AgenceRepository agenceRepository;
    private final EmployeRepository employeRepository;

    @Bean
    CommandLineRunner init() {
        return args -> {
            if (agenceRepository.count() == 0 && employeRepository.count() == 0) {
                Agence sfax = Agence.builder()
                        .nom("Agence Sfax").ville("Sfax").adresse("Rue A Sfax").telephone("74000000").build();
                Agence tunis = Agence.builder()
                        .nom("Agence Tunis").ville("Tunis").adresse("Rue B Tunis").telephone("71000000").build();
                List<Agence> saved = agenceRepository.saveAll(List.of(sfax, tunis));
                Agence sfaxSaved = saved.get(0);
                Agence tunisSaved = saved.get(1);

                Employe employe = Employe.builder()
                        .nom("Ben Ali").prenom("Sami").role(RoleEmploye.AGENT).agence(sfaxSaved).build();
                employeRepository.save(employe);
            }
        };
    }
}
