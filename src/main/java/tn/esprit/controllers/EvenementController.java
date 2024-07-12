package tn.esprit.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.entities.Club;
import tn.esprit.entities.Evenement;
import tn.esprit.entities.User;
import tn.esprit.repositories.EvenementRepository;
import tn.esprit.repositories.UserRepository;
import tn.esprit.services.ClubServices;
import tn.esprit.services.EvenementService;
import tn.esprit.services.EvenementServiceImpl;

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

@RequestMapping("/evenements")
@CrossOrigin(origins = "*")
public class EvenementController {
    @Autowired
    private EvenementServiceImpl evenementService;
    @Autowired
    private EvenementRepository evenementRepository;







    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClubServices clubService;
    public static String UPLOAD_DIRECTORY = "C:/Users/manel/Desktop/PIdevfinale/ESPRITIA-FRONT/src/assets/";


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
                                               @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin ,

                                               @RequestParam("statut") String statut,
                                               @RequestParam("siteweb") String siteweb,
                                               @RequestParam("capacite") int capacite,
                                               @RequestParam("nbrParticipant") int nbrParticipant,
                                               @RequestParam("affiche") MultipartFile affiche,
                                               @RequestParam("rating") Double rating)
                                                throws IOException {
        if (affiche.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image affiche is empty");
        }

        try {
            String afficheFile = saveFile(affiche);
            Evenement evenement = new Evenement();
            evenement.setNom(nom);
            evenement.setDate(date);
            evenement.setDateFin(dateFin);
            evenement.setStatut(statut);
            evenement.setSiteweb(siteweb);
            evenement.setCapacite(capacite);
            evenement.setNbrParticipant(nbrParticipant);
            evenement.setAffiche(afficheFile);
            evenement.setRating(rating);




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
                                              @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin,

                                              @RequestParam("statut") String statut,
                                              @RequestParam("siteweb") String siteweb,@RequestParam("capacite") int capacite,
                                              @RequestParam("nbrParticipant") int nbrParticipant,
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
        evenement.setDateFin(dateFin);

        evenement.setStatut(statut);
        evenement.setSiteweb(siteweb);
        evenement.setCapacite(capacite);
        evenement.setNbrParticipant(nbrParticipant);

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
    @PostMapping("/{evenementId}/affecter/{clubId}")
    public ResponseEntity<String> affecterEvenement(@PathVariable Long evenementId, @PathVariable Long clubId) {
        return evenementService.affecterEvenement(evenementId, clubId);
    }
    @PostMapping("/{id}/participer")
    public ResponseEntity<Evenement> participerEvenement(@PathVariable Long id) {
        log.info("Received request to participate in event with ID: " + id);
        Optional<Evenement> optionalEvenement = evenementRepository.findById(id);
        if (optionalEvenement.isPresent()) {
            Evenement evenement = optionalEvenement.get();
            log.info("Event found: " + evenement.getNom() + " with current participants: " + evenement.getNbrParticipant() + "/" + evenement.getCapacite());
            if (evenement.getNbrParticipant() < evenement.getCapacite()) {
                evenement.setNbrParticipant(evenement.getNbrParticipant() + 1);
                evenementRepository.save(evenement);
                log.info("Participation successful. Updated participants: " + evenement.getNbrParticipant());
                return ResponseEntity.ok(evenement);
            } else {
                log.warn("Event capacity reached for event ID: " + id);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        } else {
            log.error("Event not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/{evenementId}/participer/{userId}")
    public ResponseEntity<String> participerEvenement(@PathVariable Long evenementId, @PathVariable Long userId) {
        return evenementService.participerEvenement(evenementId, userId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Evenement>> getEvenementsByUserId(@PathVariable Long userId) {
        List<Evenement> evenements = evenementService.getEvenementsByUserId(userId);
        return ResponseEntity.ok(evenements);
    }

}
