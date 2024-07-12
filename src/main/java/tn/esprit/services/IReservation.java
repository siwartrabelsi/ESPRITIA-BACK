package tn.esprit.services;

import tn.esprit.entities.Reservation;

import java.util.List;

public interface IReservation {
    public Reservation addReservation(Reservation reservation);
    public Reservation updateReservation(Reservation reservation);
    public Reservation getReservationById(Long id);
    public List<Reservation> getAllReservation();
    public void deleteReservation(Long id);
    public List<Object[]> countReservationsBySpaceAndDate();
}
