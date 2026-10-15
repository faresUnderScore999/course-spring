package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Agence;

public interface AgenceRepository extends JpaRepository<Agence, Long> {

	List<Agence> findByVille(String ville);
}
