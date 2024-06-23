package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.entities.EspaceEvenement;

import java.util.List;

public interface EspaceRepository extends JpaRepository<EspaceEvenement,Long> {
    List<EspaceEvenement> findByNomContainingIgnoreCase(String nom);

}
