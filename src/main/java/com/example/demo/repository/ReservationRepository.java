package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Reservation;
import com.example.demo.enums.StatutReservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Override
	@Query("SELECT DISTINCT r FROM Reservation r "
			+ "LEFT JOIN FETCH r.vehicule v LEFT JOIN FETCH v.agence "
			+ "LEFT JOIN FETCH r.client LEFT JOIN FETCH r.contrat")
	List<Reservation> findAll();

	@Override
	@Query("SELECT DISTINCT r FROM Reservation r "
			+ "LEFT JOIN FETCH r.vehicule v LEFT JOIN FETCH v.agence "
			+ "LEFT JOIN FETCH r.client LEFT JOIN FETCH r.contrat "
			+ "WHERE r.idReservation = :id")
	Optional<Reservation> findById(@Param("id") Long id);

	List<Reservation> findByStatut(StatutReservation statut);

	List<Reservation> findByClient_IdClient(Long idClient);

	List<Reservation> findByVehicule_IdVehicule(Long idVehicule);
}
