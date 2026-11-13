package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entity.Agence;
import com.example.demo.entity.Employe;
import com.example.demo.enums.RoleEmploye;
import com.example.demo.repository.AgenceRepository;
import com.example.demo.repository.EmployeRepository;

/**
 * End-to-end smoke test of the Employe / Agence {@link ManyToOne} mutation:
 * an employee initially assigned to Sfax is d\u00e9saffect\u00e9 then affect\u00e9 to Tunis.
 */
@SpringBootTest
@AutoConfigureMockMvc
class EmployeMutationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private EmployeRepository employeRepository;

    private Long sfaxId;
    private Long tunisId;
    private Long employeId;

    @BeforeEach
    void setUp() {
        Agence sfax = agenceRepository.save(Agence.builder()
                .nom("Agence Sfax").ville("Sfax").adresse("Rue A Sfax").telephone("74000000").build());
        Agence tunis = agenceRepository.save(Agence.builder()
                .nom("Agence Tunis").ville("Tunis").adresse("Rue B Tunis").telephone("71000000").build());
        sfaxId = sfax.getIdAgence();
        tunisId = tunis.getIdAgence();

        Employe employe = employeRepository.save(Employe.builder()
                .nom("Ben Ali").prenom("Sami").role(RoleEmploye.AGENT).agence(sfax).build());
        employeId = employe.getIdEmploye();
    }

    @Test
    void desaffecterThenAffecterToTunis() throws Exception {
        // 2. D\u00e9saffecter: employe no longer attached to any agency
        mockMvc.perform(put("/api/employes/{idEmploye}/desaffecter", employeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAgence").doesNotExist());

        // 3. Affecter to Tunis
        mockMvc.perform(put("/api/employes/{idEmploye}/affecter/{idAgence}", employeId, tunisId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAgence").value(tunisId.intValue()))
                .andExpect(jsonPath("$.nom").value("Ben Ali"));
    }

    @Test
    void desaffecterUnknownEmployeReturns404() throws Exception {
        mockMvc.perform(put("/api/employes/{idEmploye}/desaffecter", 999999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void affecterUnknownAgenceReturns404() throws Exception {
        mockMvc.perform(put("/api/employes/{idEmploye}/affecter/{idAgence}", employeId, 999999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void affecterUnknownEmployeReturns404() throws Exception {
        mockMvc.perform(put("/api/employes/{idEmploye}/affecter/{idAgence}", 999999L, tunisId))
                .andExpect(status().isNotFound());
    }
}
