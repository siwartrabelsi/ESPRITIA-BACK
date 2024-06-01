package tn.esprit.services;

import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.entities.Covoiturage;
import tn.esprit.repositories.CovoiturageRepository;

import java.util.List;

public class CovoiturageServices implements ICovoiturageServices {

    private CovoiturageRepository covoiturageRepo;

    @Override
    public void addCovoiturage(Covoiturage covoiturage) {
        covoiturageRepo.save(covoiturage);

    }

    public Covoiturage getCovoiturageById(Long id) {

        return covoiturageRepo.findById(id).orElse(null);
    }

    public List<Covoiturage> getAllCovoiturages() {
        return covoiturageRepo.findAll();
    }

    public void updateCovoiturage(Covoiturage covoiturage) {
        covoiturageRepo.save(covoiturage);
    }

    public void deleteCovoiturage(Long id) {
        covoiturageRepo.deleteById(id);
    }
}
