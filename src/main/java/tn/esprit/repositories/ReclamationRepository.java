package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.User;

import java.util.List;
import java.util.Optional;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    @Query("SELECT u.statut, COUNT(u) FROM Reclamation u GROUP BY u.statut")
    List<Object[]> countReclamationsByStatut();
    Optional<Reclamation> findFirstByUserOrderBySendAtDesc(User user);
}
