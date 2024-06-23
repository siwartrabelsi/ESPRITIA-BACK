package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.entities.Covoiturage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CovoiturageRepository extends JpaRepository<Covoiturage, Long> {
    List<Covoiturage> findAllByUserId(Long userId);
    Optional<Covoiturage> findByIdAndUserId(Long id, Long userId);


    @Modifying
    @Query("DELETE FROM Covoiturage c WHERE c.id = :id AND c.user.id = :userId")
    void deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
