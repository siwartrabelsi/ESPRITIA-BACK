package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE (:date BETWEEN r.datedebut AND r.datefin)")
    List<Reservation> findOverlappingReservations(@Param("date") LocalDate date);

    // Méthode personnalisée pour compter les réservations par espace et date de début
    @Query("SELECT r.espace.id, r.datedebut, COUNT(r.id) " +
            "FROM Reservation r " +
            "GROUP BY r.espace.id, r.datedebut " +
            "ORDER BY r.espace.id, r.datedebut")
    List<Object[]> countReservationsBySpaceAndDate();
}
