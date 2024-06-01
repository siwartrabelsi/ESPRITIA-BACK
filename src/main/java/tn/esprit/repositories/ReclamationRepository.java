package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.entities.Reclamation;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
}
