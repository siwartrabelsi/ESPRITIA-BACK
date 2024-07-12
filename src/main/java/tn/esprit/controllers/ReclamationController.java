package tn.esprit.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.Dto.UpdateReclamationRequest;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.ReclamationStatus;
import tn.esprit.services.*;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/reclamation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamationController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIR = "./uploads";

    @Autowired
    private IReclamationServices reclamationServices;
    @Autowired
    private ISentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private IUserServices userServices;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<?> addReclamation(@RequestParam("description") String description, @RequestParam("image") MultipartFile imageFile, @RequestHeader("Authorization") String authToken)/* throws ServletException, NullPointerException */{
        try {
            String jwt = authToken.substring(7);

           String username = jwtService.extractUserName(jwt);
        String imageUrl = reclamationServices.saveImage(imageFile);
        Reclamation reclamation = new Reclamation();
        reclamation.setDescription(description);
        reclamation.setImageUrl(imageUrl);
        reclamation.setSendAt(LocalDateTime.now());
        reclamation.setStatut(ReclamationStatus.INPROGRESS);
       reclamation.setUser(userServices.getUserByUserName(username));
            Optional<Reclamation> lastReclamation = reclamationServices.getLastReclamationForUser(reclamation.getUser());
           if (lastReclamation.isPresent()) {
                Reclamation lstRec = lastReclamation.get();
               Duration durationBetween = Duration.between(lstRec.getSendAt(), reclamation.getSendAt());
               long durationInSeconds = durationBetween.getSeconds();
                if(durationInSeconds <= 120){
                    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                            .body("Too many requests. Please wait before submitting another reclamation.");
                }else {
                    Reclamation createdReclamation = reclamationServices.addReclamation(reclamation);
                    return ResponseEntity.ok(createdReclamation);
                }
            }
            Reclamation createdReclamation = reclamationServices.addReclamation(reclamation);
        return ResponseEntity.ok(createdReclamation);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        Reclamation reclamation = reclamationServices.getReclamationById(id);
        if (reclamation != null) {
            return ResponseEntity.ok(reclamation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Set<Reclamation>> getAllReclamations() {
        Set<Reclamation> reclamations = reclamationServices.displayReclamation();
        return ResponseEntity.ok(reclamations);
    }

    @PutMapping("/{id}/traiter")
    public ResponseEntity<Reclamation> traiterReclamation(
            @PathVariable Long id,
            @RequestBody UpdateReclamationRequest updatedReclamation
    ) {
        Reclamation reclamation = reclamationServices.getReclamationById(id);
        if (reclamation != null) {
            reclamation.setStatut(updatedReclamation.getSelectedStatus());
            reclamation.setReponse(updatedReclamation.getReponse());

            Reclamation updated = reclamationServices.updateReclamation(id, reclamation);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        reclamationServices.deleteReclamation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) throws IOException {
        Path imagePath = Paths.get(UPLOAD_DIR).resolve(fileName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Image not found: " + fileName);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // Adjust content type as per your image type

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getReclamationByStatus() {
        Map<String, Long> reclamationByStatus = reclamationServices.getReclamationByStatus();
        return new ResponseEntity<>(reclamationByStatus, HttpStatus.OK);
    }
    @GetMapping("/sentiments")
    public ResponseEntity<Map<String, List<String>>> getReclamationsSentimentsByUser() {
        Set<Reclamation> allReclamations = reclamationServices.displayReclamation();
        Map<String, List<String>> sentimentsByUser = sentimentAnalysisService.analyzeSentimentsByUser(List.copyOf(allReclamations));
        return ResponseEntity.ok(sentimentsByUser);
    }

}
