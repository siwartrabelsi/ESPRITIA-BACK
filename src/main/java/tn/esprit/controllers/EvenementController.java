package tn.esprit.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.entities.Evenement;
import tn.esprit.repositories.EvenementRepository;
import tn.esprit.services.EvenementService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/evenements")
public class EvenementController {
    @Autowired
    private EvenementService evenementService;
    @Autowired
    private EvenementRepository evenementRepository;
    public static String UPLOAD_DIRECTORY = "C:/Users/manel/Desktop/evenement-management/evenement-management/evenement-management/src/assets/";

    @GetMapping
    public List<Evenement> getAllEvenements() {
        return evenementService.getAllEvenements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evenement> getEvenementById(@PathVariable Long id) {
        Evenement evenement = evenementService.getEvenementById(id);
        if (evenement != null) {
            return ResponseEntity.ok(evenement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Evenement createEvenement(@RequestBody Evenement evenement) {
        return evenementService.createEvenement(evenement);
    }
    private String saveFile(MultipartFile file) throws IOException {
        String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String fileName = currentDate + "_" + file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
        Files.write(path, bytes);
        return fileName;
    }
    @PostMapping("/ajouter")
    public ResponseEntity<String> addEvenement(@RequestParam("nom") String nom,
                                               @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date ,
                                               @RequestParam("statut") String statut,
                                               //@RequestParam("organisateur_id") Long organisateurId,
                                               @RequestParam("affiche") MultipartFile affiche,@RequestParam("rating") Double rating) throws IOException {
        if (affiche.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image affiche is empty");
        }

        try {
            String afficheFile = saveFile(affiche);
            Evenement evenement = new Evenement();
            evenement.setNom(nom);
            evenement.setDate(date);
            evenement.setStatut(statut);
            evenement.setAffiche(afficheFile);
            evenement.setRating(rating);


            // Assuming you have a method to fetch the organisateur by ID
//            User organisateur = userRepository.findById(organisateurId)
//                    .orElseThrow(() -> new RuntimeException("Organisateur not found"));
//            evenement.setOrganisateur(organisateur);

            evenementRepository.save(evenement);

            String message = "Evenement added successfully";
            return ResponseEntity.ok(message);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }



private void deleteFile(String fileName) throws IOException {
    Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
    Files.deleteIfExists(path);
}
@PutMapping("/{id}")
public ResponseEntity<String> updateEvenement(@PathVariable Long id,
                                              @RequestParam("nom") String nom,
                                              @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                              @RequestParam("statut") String statut,
                                              @RequestParam(value = "affiche", required = false) MultipartFile affiche) {
    Optional<Evenement> evenementOptional = evenementRepository.findById(id);
    if (!evenementOptional.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    Evenement evenement = evenementOptional.get();

    try {
        if (affiche != null && !affiche.isEmpty()) {
            // Supprimer l'ancienne affiche
            deleteFile(evenement.getAffiche());
            // Traitement de la nouvelle affiche
            String afficheFileName = saveFile(affiche);
            evenement.setAffiche(afficheFileName);
        }

        // Mettre à jour les autres informations de l'événement
        evenement.setNom(nom);
        evenement.setDate(date);
        evenement.setStatut(statut);

        // Enregistrer les modifications dans la base de données
        evenementRepository.save(evenement);

        // Message de succès
        String message = "Evenement updated successfully";
        return ResponseEntity.ok(message);
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable Long id) {
        evenementService.deleteEvenement(id);
        return ResponseEntity.noContent().build();
    }
}
