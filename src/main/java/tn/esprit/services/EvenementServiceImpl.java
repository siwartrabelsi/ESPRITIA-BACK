package tn.esprit.services;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.esprit.entities.Club;
import tn.esprit.entities.Evenement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entities.Evenement;
import tn.esprit.entities.User;
import tn.esprit.repositories.ClubRepository;
import tn.esprit.repositories.EvenementRepository;
import tn.esprit.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class EvenementServiceImpl implements EvenementService {
    @Autowired
    private EvenementRepository evenementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClubRepository clubRepository;

    @Override
    public List<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }

    @Override
    public Evenement getEvenementById(Long id) {
        return evenementRepository.findById(id).orElse(null);
    }

    @Override
    public Evenement createEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    @Override
    public Evenement updateEvenement(Long id, Evenement evenement) {
        Optional<Evenement> existingEvenement = evenementRepository.findById(id);
        if (existingEvenement.isPresent()) {
            evenement.setId(id);
            return evenementRepository.save(evenement);
        }
        return null;
    }

    @Override
    public void deleteEvenement(Long id) {
        evenementRepository.deleteById(id);
    }
    @Transactional
    public ResponseEntity<String> participerEvenement(Long eventId, Long userId) {
        Optional<Evenement> optionalEvenement = evenementRepository.findById(eventId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalEvenement.isPresent() && optionalUser.isPresent()) {
            Evenement evenement = optionalEvenement.get();
            User user = optionalUser.get();

            // Check if user already participated in this event
            boolean alreadyParticipated = evenement.getParticipants().contains(user);
            if (alreadyParticipated) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Vous avez déjà participé à cet événement.");
            }

            // Check if event has reached its capacity
            if (evenement.getNbrParticipant() <= 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("La capacité maximale de cet événement est atteinte.");
            }

            // Add user to participants and update participant count
            evenement.getParticipants().add(user);
            evenement.setNbrParticipant(evenement.getNbrParticipant() - 1); // Decrease participant count
            evenementRepository.save(evenement);

            return ResponseEntity.ok("Vous avez participé avec succès à l'événement.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Evenement findById(Long id) {
        Optional<Evenement> optionalEvenement = evenementRepository.findById(id);
        return optionalEvenement.orElse(null);
    }

    public ResponseEntity<String> affecterEvenement(Long evenementId, Long clubId) {
        Optional<Evenement> optionalEvenement = evenementRepository.findById(evenementId);
        if (optionalEvenement.isPresent()) {
            Evenement evenement = optionalEvenement.get();
            Optional<Club> optionalClub = clubRepository.findById(clubId);
            if (optionalClub.isPresent()) {
                Club club = optionalClub.get();
                evenement.getClubs().add(club);
                evenementRepository.save(evenement);
                return ResponseEntity.ok("Evenement affecté au club avec succès");
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public List<Evenement> getEvenementsByUserId(Long userId) {
        return evenementRepository.findByParticipantsId(userId);
    }
}

