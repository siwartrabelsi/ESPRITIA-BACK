package tn.esprit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entities.Reservation;
import tn.esprit.repositories.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements IReservation{

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public Reservation addReservation(Reservation reservation) {

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {

        if (reservationRepository.existsById(reservation.getId())) {
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Reservation avec ID " + reservation.getId() + " non trouvé.");
        }
    }


    @Override
    public Reservation getReservationById(Long id) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            // Accéder à l'ID de l'espace et le stocker dans la réservation
            Long espaceId = reservation.getEspaceId();
            String nomEspace = reservation.getEspacenName();
            // Faites ce que vous voulez avec espaceId
            return reservation;
        } else {
            // Gérer le cas où la réservation n'est pas trouvée
            return null;
        }
    }

    @Override
    public List<Reservation> getAllReservation() {

        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            // Accéder à l'ID de l'espace et le stocker dans la réservation
            Long espaceId = reservation.getEspaceId();
            // Faites ce que vous voulez avec espaceId
        }
        return reservations;
    }

    @Override
    public void deleteReservation(Long id) {

        reservationRepository.deleteById(id);
    }

    @Override
    public List<Object[]> countReservationsBySpaceAndDate() {
        return reservationRepository.countReservationsBySpaceAndDate();
    }
}
