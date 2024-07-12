package tn.esprit.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.entities.Covoiturage;
import tn.esprit.entities.IStatutCovoiturage;
import tn.esprit.repositories.CovoiturageRepository;
import tn.esprit.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CovoiturageServices implements ICovoiturageServices {

    private final CovoiturageRepository covoiturageRepository;
    private final UserRepository userRepository;
    private EmailService emailService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addCovoiturage(Long userId, Covoiturage covoiturage) {
        covoiturage.setUser(userRepository.findById(userId).orElse(null));
        covoiturageRepository.save(covoiturage);
    }

    public Covoiturage getCovoiturageById(Long userId, Long id) {
        return covoiturageRepository.findByIdAndUserId(id, userId).orElse(null);
    }

    public List<Covoiturage> getAllCovoiturages(Long userId) {
        return covoiturageRepository.findAllByUserId(userId);
    }

    public void updateCovoiturage(Long userId, Covoiturage covoiturage) {
        covoiturage.setUser(userRepository.findById(userId).orElse(null));
        covoiturageRepository.save(covoiturage);
    }

    @Transactional
    public void deleteCovoiturage(Long id, Long userId) {
        covoiturageRepository.deleteByIdAndUserId(id, userId);
    }
    @Override
    public List<Covoiturage> searchCovoiturages(String fumeur, Date dateDepart, String lieuDepart, String destination) {
        return covoiturageRepository.searchCovoiturages(fumeur, dateDepart, lieuDepart, destination);
    }
    @Transactional
    public void sendReservationEmail(Covoiturage covoiturage) {
        String subject = "Reservation Confirmation";
        String message = "Your reservation for the carpool from " + covoiturage.getLieuDepart() + " to " + covoiturage.getDestination() + " on " + covoiturage.getHeureDepart() + " has been confirmed.";
        emailService.sendEmail(covoiturage.getUser().getEmail(), subject, message);

    }





}
