package tn.esprit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entities.EspaceEvenement;
import tn.esprit.repositories.EspaceRepository;

import java.util.List;
@Service
public class EspaceServiceImpl implements IEspaceService {
    @Autowired
    EspaceRepository espaceRepository;
    @Override
    public EspaceEvenement addEspace(EspaceEvenement espace) {

        return espaceRepository.save(espace);
    }

    @Override
    public EspaceEvenement updateEspace(EspaceEvenement espace) {
        if (espaceRepository.existsById(espace.getId())) {
            return espaceRepository.save(espace);
        } else {
            throw new RuntimeException("Espace avec ID " + espace.getId() + " non trouvé.");
        }
    }
    @Override
    public EspaceEvenement getEspaceById(Long id) {

        return espaceRepository.findById(id).orElse(null);
    }

    @Override
    public List<EspaceEvenement> getAllEspace() {

        return espaceRepository.findAll();
    }

    @Override
    public void deleteEspace(Long id) {
        espaceRepository.deleteById(id);

    }

    @Override
    public List<EspaceEvenement> findByNom(String nom) {
        return espaceRepository.findByNomContainingIgnoreCase(nom);
    }
}
