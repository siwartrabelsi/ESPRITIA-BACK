package tn.esprit.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entities.Covoiturage;
import tn.esprit.repositories.CovoiturageRepository;
import tn.esprit.repositories.UserRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class CovoiturageServices implements ICovoiturageServices {

    private CovoiturageRepository covoiturageRepository;
    private UserRepository userRepository;

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

}
