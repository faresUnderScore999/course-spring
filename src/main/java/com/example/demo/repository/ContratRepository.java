package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Contrat;

public interface ContratRepository extends JpaRepository<Contrat, Long> {

	List<Contrat> findByValide(Boolean valide);
}
