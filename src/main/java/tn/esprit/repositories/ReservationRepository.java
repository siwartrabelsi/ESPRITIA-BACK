package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
