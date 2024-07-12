package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.entities.Evenement;
import tn.esprit.entities.User;

import java.util.List;
import java.util.Optional;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
//    boolean existsByParticipantsContainingAndId(User user, Long id);
//
@Query("SELECT e FROM Evenement e JOIN e.participants p WHERE p.id = :userId")
List<Evenement> findByParticipantsId(@Param("userId") Long userId);

}
