package tn.esprit.services;

import tn.esprit.entities.Covoiturage;
import tn.esprit.entities.IStatutCovoiturage;

import java.util.Date;
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
    List<Covoiturage> searchCovoiturages(String fumeur, Date dateDepart, String lieuDepart, String destination);


    void sendReservationEmail(Covoiturage covoiturage);

}
