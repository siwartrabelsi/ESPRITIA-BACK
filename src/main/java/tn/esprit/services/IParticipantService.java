package tn.esprit.services;

import tn.esprit.entities.Participant;


import java.util.List;
import java.util.Optional;

public interface IParticipantService {

    List<Participant> getAllParticipants();

    Optional<Participant> getParticipantById(Long id);

    Participant createParticipant(Participant participant, Long formationId);

    Participant updateParticipant(Long id, Participant participant);

    void deleteParticipant(Long id);


}
