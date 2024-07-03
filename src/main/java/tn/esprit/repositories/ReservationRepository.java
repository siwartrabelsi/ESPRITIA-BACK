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
}
