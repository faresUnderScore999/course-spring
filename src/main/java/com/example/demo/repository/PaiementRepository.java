package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

	List<Paiement> findByContrat_IdContrat(Long idContrat);
}
