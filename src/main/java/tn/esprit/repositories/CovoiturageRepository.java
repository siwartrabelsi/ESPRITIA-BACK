package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tn.esprit.entities.Covoiturage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.entities.IStatutCovoiturage;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface CovoiturageRepository extends JpaRepository<Covoiturage, Long> , JpaSpecificationExecutor<Covoiturage> {
    List<Covoiturage> findAllByUserId(Long userId);
    Optional<Covoiturage> findByIdAndUserId(Long id, Long userId);
    @Modifying
    @Query("DELETE FROM Covoiturage c WHERE c.id = :id AND c.user.id = :userId")
    void deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
    @Query("SELECT c FROM Covoiturage c " +
            "WHERE (:fumeur IS NULL OR c.fumeur = :fumeur) " +
            "AND (:dateDepart IS NULL OR c.heureDepart >= :dateDepart) " +
            "AND (:lieuDepart IS NULL OR c.lieuDepart LIKE %:lieuDepart%) " +
            "AND (:destination IS NULL OR c.destination LIKE %:destination%)")
    List<Covoiturage> searchCovoiturages(@Param("fumeur") String fumeur,
                                         @Param("dateDepart") Date dateDepart,
                                         @Param("lieuDepart") String lieuDepart,
                                         @Param("destination") String destination);
}
