package tn.esprit.services;
import tn.esprit.entities.Evenement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.repositories.EvenementRepository;

import java.util.Optional;


@Service
public class EvenementServiceImpl implements EvenementService {
    @Autowired
    private EvenementRepository evenementRepository;

    @Override
    public List<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }

    @Override
    public Evenement getEvenementById(Long id) {
        return evenementRepository.findById(id).orElse(null);
    }

    @Override
    public Evenement createEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    @Override
    public Evenement updateEvenement(Long id, Evenement evenement) {
        Optional<Evenement> existingEvenement = evenementRepository.findById(id);
        if (existingEvenement.isPresent()) {
            evenement.setId(id);
            return evenementRepository.save(evenement);
        }
        return null;
    }

    @Override
    public void deleteEvenement(Long id) {
        evenementRepository.deleteById(id);
    }

}

