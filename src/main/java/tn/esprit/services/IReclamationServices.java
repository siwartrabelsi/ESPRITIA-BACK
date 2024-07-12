package tn.esprit.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.User;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IReclamationServices {
    public Reclamation addReclamation(Reclamation rec);
    public Set<Reclamation> displayReclamation();
    Reclamation updateReclamation(Long id, Reclamation reclamation);

    public void deleteReclamation(long id);
    public Reclamation getReclamationById(Long id);
    String saveImage(MultipartFile imageFile) throws IOException;
    public Map<String, Long> getReclamationByStatus();
    public Optional<Reclamation> getLastReclamationForUser(User user);
}
