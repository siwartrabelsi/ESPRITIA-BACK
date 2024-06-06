package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.entities.EspaceEvenement;

public interface EspaceRepository extends JpaRepository<EspaceEvenement,Long> {

}
