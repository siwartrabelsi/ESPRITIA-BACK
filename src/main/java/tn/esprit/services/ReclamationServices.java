package tn.esprit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.ReclamationStatus;
import tn.esprit.entities.User;
import tn.esprit.repositories.ReclamationRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ReclamationServices implements IReclamationServices {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Override
    public Reclamation addReclamation(Reclamation rec) {
        return reclamationRepository.save(rec);
    }

    @Override
    public Set<Reclamation> displayReclamation() {
        return new HashSet<>(reclamationRepository.findAll());
    }

    @Override
    public Reclamation updateReclamation(Long id, Reclamation updatedReclamation) {
        Reclamation existingReclamation = reclamationRepository.findById(id).orElse(null);
        if (existingReclamation != null) {
            existingReclamation.setStatut(updatedReclamation.getStatut());
            existingReclamation.setReponse(updatedReclamation.getReponse());
            // You can add more fields as needed
            return reclamationRepository.save(existingReclamation);
        }
        return null; // Handle not found case
    }

    @Override
    public void deleteReclamation(long id) {
        reclamationRepository.deleteById(id);
    }

    @Override
    public Reclamation getReclamationById(Long id) {
        Optional<Reclamation> reclamationOptional = reclamationRepository.findById(id);
        return reclamationOptional.orElse(null);
    }
    @Override
    public String saveImage(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        String uploadDir = "./uploads";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(imageFile.getInputStream(), filePath);
        return fileName;
    }
    @Override
    public Map<String, Long> getReclamationByStatus() {
        List<Object[]> data = reclamationRepository.countReclamationsByStatut();
        Map<String, Long> result = new HashMap<>();

        for (Object[] row : data) {
            ReclamationStatus rs = (ReclamationStatus) row[0];
            Long count = (Long) row[1];
            result.put(rs.name(), count);
        }

        return result;
    }
    @Override
    public Optional<Reclamation> getLastReclamationForUser(User user) {
        return reclamationRepository.findFirstByUserOrderBySendAtDesc(user);
    }
}
