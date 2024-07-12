package tn.esprit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entities.Participant;
import tn.esprit.entities.Formation;
import tn.esprit.repositories.ParticipantRepository;
import tn.esprit.repositories.FormationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService implements IParticipantService {

    private final ParticipantRepository participantRepository;
    private final FormationRepository formationRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, FormationRepository formationRepository) {
        this.participantRepository = participantRepository;
        this.formationRepository = formationRepository;
    }

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Override
    public Optional<Participant> getParticipantById(Long id) {
        return participantRepository.findById(id);
    }

    @Override
    public Participant createParticipant(Participant participant, Long formationId) {
        Optional<Formation> formationOptional = formationRepository.findById(formationId);
        if (formationOptional.isPresent()) {
            participant.setFormation(formationOptional.get());
            return participantRepository.save(participant);
        } else {
            return  null;
        }
    }

    @Override
    public Participant updateParticipant(Long id, Participant updatedParticipant) {
        Optional<Participant> existingParticipantOptional = participantRepository.findById(id);
        if (existingParticipantOptional.isPresent()) {
            Participant existingParticipant = existingParticipantOptional.get();
            existingParticipant.setNom(updatedParticipant.getNom());
            existingParticipant.setEmail(updatedParticipant.getEmail());
            // Mettre à jour d'autres propriétés si nécessaire
            return participantRepository.save(existingParticipant);
        } else {
            throw new IllegalArgumentException("Participant with ID " + id + " not found");
        }
    }

    @Override
    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }


}
