package tn.esprit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entities.Reservation;
import tn.esprit.repositories.ReservationRepository;

import java.util.List;

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

        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> getAllReservation() {

        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(Long id) {

        reservationRepository.deleteById(id);
    }
}
