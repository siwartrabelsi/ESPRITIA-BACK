package tn.esprit.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entities.Club;
import tn.esprit.entities.Formation;
import tn.esprit.repositories.ClubRepository;
import tn.esprit.repositories.FormationRepository;


import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class FormationService implements IFormationService {

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    @Override
    public Formation createFormation(Formation formation, Long clubId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        if (clubOpt.isPresent()) {
            Club club = clubOpt.get();
            formation.setClub(club);
            return formationRepository.save(formation);
        }
        return null; // or throw an exception if club is not found
    }

    @Override
    public Formation updateFormation(Long id, Formation formation) {
        Optional<Formation> formationOpt = formationRepository.findById(id);
        if (formationOpt.isPresent()) {
            Formation existingFormation = formationOpt.get();
            existingFormation.setNom(formation.getNom());
            existingFormation.setDescription(formation.getDescription());
            existingFormation.setDateDebut(formation.getDateDebut());
            existingFormation.setDateFin(formation.getDateFin());
            return formationRepository.save(existingFormation);
        }
        return null; // or throw an exception if formation is not found
    }

    @Override
    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }
    @Override
    public List<Formation> getFormationsByNom(String nom) {
        return formationRepository.findByNom(nom); // Supposant que votre repository possède une méthode findByNom
    }


}
