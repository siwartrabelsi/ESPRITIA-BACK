package tn.esprit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entities.EspaceEvenement;
import tn.esprit.entities.Reservation;
import tn.esprit.repositories.EspaceRepository;
import tn.esprit.repositories.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class EspaceServiceImpl implements IEspaceService {
    @Autowired
    EspaceRepository espaceRepository;
    @Autowired
    ReservationRepository reservationRepository;

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

    @Override
    public List<EspaceEvenement> getEspacesDisponibles(LocalDate date) {
        // Récupérer toutes les réservations qui chevauchent la date spécifiée
        List<Reservation> reservations = reservationRepository.findOverlappingReservations(date);

        // Récupérer tous les espaces
        List<EspaceEvenement> allSpaces = espaceRepository.findAll();

        // Filtrer les espaces disponibles
        List<EspaceEvenement> espacesDisponibles = new ArrayList<>();
        for (EspaceEvenement espace : allSpaces) {
            boolean estDisponible = true;
            for (Reservation reservation : reservations) {
                if (espace.getId().equals(reservation.getEspace().getId())) {
                    LocalDate debutReservation = reservation.getDatedebut();
                    LocalDate finReservation = reservation.getDatefin();
                    // Vérifier si la date spécifiée ne se trouve pas entre le début et la fin de la réservation
                    if (date.isAfter(debutReservation.minusDays(1)) && date.isBefore(finReservation.plusDays(1))) {
                        estDisponible = false;
                        break;
                    }
                }
            }
            if (estDisponible) {
                espacesDisponibles.add(espace);
            }
        }
        return espacesDisponibles;
    }
}
