package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
