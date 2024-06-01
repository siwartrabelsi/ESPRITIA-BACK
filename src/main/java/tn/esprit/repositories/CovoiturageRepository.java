package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.entities.Covoiturage;


public interface CovoiturageRepository extends JpaRepository<Covoiturage, Long> {

}
