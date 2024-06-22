package tn.esprit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.Reservation;
import tn.esprit.services.IReservation;

import java.util.List;
@RestController
@RequestMapping("api/reservation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationRestController {
    @Autowired
    IReservation reservations;

    @PostMapping("/add")
    public Reservation addReservation(@RequestBody Reservation reservation){
        return reservations.addReservation(reservation);
    }
    @PutMapping("/update/{id}")
    public Reservation updateReservation(@PathVariable("id") Long id, @RequestBody Reservation reservation ){

        // Vérifier si l'entité avec cet ID existe
        Reservation existingReservation = reservations.getReservationById(id);
        if (existingReservation != null) {
            // Mettre à jour les champs de l'entité existante
            existingReservation.setDatedebut(reservation.getDatedebut());
            existingReservation.setDatefin(reservation.getDatefin());
            existingReservation.setClubOrganisateur(reservation.getClubOrganisateur());
            existingReservation.setTypeEvenement(reservation.getTypeEvenement());
            existingReservation.setNombreParicipantsAttendus(reservation.getNombreParicipantsAttendus());
            existingReservation.setHeureDebut(reservation.getHeureDebut()); // Mise à jour de l'heure de début
            existingReservation.setHeureFin(reservation.getHeureFin());     // Mise à jour de l'heure de fin
            existingReservation.setEspace(reservation.getEspace());
            // Sauvegarder l'entité mise à jour
            return reservations.updateReservation(existingReservation);
        } else {
            throw new RuntimeException("Reservation avec ID " + id + " non trouvée.");
        }
    }
    @GetMapping("/get/{id}")
    public Reservation getReservationById(@PathVariable("id")Long id){

        return reservations.getReservationById(id);
    }
    @GetMapping("/getAll")
    public List<Reservation> getAllReservation(){

        return reservations.getAllReservation();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable("id") Long id){
        reservations.deleteReservation(id);}
}
