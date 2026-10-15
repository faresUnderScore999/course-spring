package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Vehicule;
import com.example.demo.enums.StatutVehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

	@Override
	@Query("SELECT DISTINCT v FROM Vehicule v LEFT JOIN FETCH v.agence")
	List<Vehicule> findAll();

	@Override
	@Query("SELECT DISTINCT v FROM Vehicule v LEFT JOIN FETCH v.agence WHERE v.idVehicule = :id")
	Optional<Vehicule> findById(@Param("id") Long id);

	List<Vehicule> findByStatut(StatutVehicule statut);

	List<Vehicule> findByAgence_IdAgence(Long idAgence);
}
