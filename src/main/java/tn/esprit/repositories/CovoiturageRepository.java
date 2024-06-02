package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.entities.Covoiturage;

import java.util.List;
import java.util.Optional;


public interface CovoiturageRepository extends JpaRepository<Covoiturage, Long> {
    List<Covoiturage> findAllByUserId(Long userId);
    Optional<Covoiturage> findByIdAndUserId(Long id, Long userId);
    void deleteByIdAndUserId(Long id, Long userId);
}
