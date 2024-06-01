package tn.esprit.services;

import tn.esprit.entities.Covoiturage;

import java.util.List;

public interface ICovoiturageServices {
    // Create
    void addCovoiturage(Covoiturage covoiturage);

    // Read
    Covoiturage getCovoiturageById(Long id);
    List<Covoiturage> getAllCovoiturages();

    // Update
    void updateCovoiturage(Covoiturage covoiturage);

    // Delete
   void deleteCovoiturage(Long id);
}
