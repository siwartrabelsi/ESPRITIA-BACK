package tn.esprit.services;

import tn.esprit.entities.Reclamation;
import tn.esprit.repositories.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ReclamationServices implements IReclamationServices {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Override
    public Reclamation addReclamation(Reclamation rec) {
        return reclamationRepository.save(rec);
    }

    @Override
    public Set<Reclamation> displayReclamation() {
        return new HashSet<>(reclamationRepository.findAll());
    }

    @Override
    public Reclamation updateReclamation(long id, Reclamation rec) {
        if (reclamationRepository.existsById(id)) {
            rec.setId(id);
            return reclamationRepository.save(rec);
        }
        return null;
    }

    @Override
    public void deleteReclamation(long id) {
        reclamationRepository.deleteById(id);
    }

    @Override
    public Reclamation getReclamationById(Long id) {
        Optional<Reclamation> reclamationOptional = reclamationRepository.findById(id);
        return reclamationOptional.orElse(null);
    }
}
