package tn.esprit.services;

import tn.esprit.entities.Formation;

import java.util.List;
import java.util.Optional;

public interface IFormationService {

    List<Formation> getAllFormations();

    Optional<Formation> getFormationById(Long id);

    Formation createFormation(Formation formation, Long clubId);

    Formation updateFormation(Long id, Formation formation);

    void deleteFormation(Long id);
}
