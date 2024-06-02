package tn.esprit.services;

import tn.esprit.entities.Covoiturage;

import java.util.List;

public interface ICovoiturageServices {
    // Create
    void addCovoiturage(Long userId ,Covoiturage covoiturage);

    // Read
    Covoiturage getCovoiturageById(Long userId ,Long id);
    List<Covoiturage> getAllCovoiturages(Long userId);

    // Update
    void updateCovoiturage(Long userId,Covoiturage covoiturage);

    // Delete
   void deleteCovoiturage(Long userId,Long id);
}
