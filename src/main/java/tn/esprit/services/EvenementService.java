package tn.esprit.services;
import tn.esprit.entities.Evenement;

import java.util.List;

public interface EvenementService {
    List<Evenement> getAllEvenements();
    Evenement getEvenementById(Long id);
    Evenement createEvenement(Evenement evenement);
    Evenement updateEvenement(Long id, Evenement evenement);
    void deleteEvenement(Long id);
}
