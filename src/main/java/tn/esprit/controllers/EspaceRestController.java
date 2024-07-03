package tn.esprit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.entities.EspaceEvenement;
import tn.esprit.services.IEspaceService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/espace")
@CrossOrigin(origins = "http://localhost:4200")
public class EspaceRestController {
    @Autowired
    IEspaceService espaces;
    private final String uploadDir = "uploads/";
    @PostMapping("/add")
    public EspaceEvenement addEspace(@RequestBody EspaceEvenement espace){

        return espaces.addEspace(espace);
    }
    @PutMapping("/update/{id}")
    public EspaceEvenement updateEspace(@PathVariable("id") Long id ,@RequestBody EspaceEvenement espace ){
        EspaceEvenement existingEspace = espaces.getEspaceById(id);
        if (existingEspace != null) {
            existingEspace.setNom(espace.getNom());
            existingEspace.setDescription(espace.getDescription());
            existingEspace.setCapacite(espace.getCapacite());
            existingEspace.setAdresse(espace.getAdresse());
            existingEspace.setEquipement(espace.getEquipement());
            existingEspace.setPhoto(espace.getPhoto());
            return espaces.updateEspace(existingEspace);
        } else {
            throw new RuntimeException("Espace avec ID " + id + " non trouvé.");
        }
    }
    @GetMapping("/get/{id}")
    public EspaceEvenement getCourById(@PathVariable("id")Long id){

        return espaces.getEspaceById(id);
    }
    @GetMapping("/getAll")
    public List<EspaceEvenement> getAllEspace(){

        return espaces.getAllEspace();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteEspace(@PathVariable("id") Long id){
        espaces.deleteEspace(id);}

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<String> uploadImage(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
        EspaceEvenement espace = espaces.getEspaceById(id);
        if (espace == null) {
            return new ResponseEntity<>("Espace not found", HttpStatus.NOT_FOUND);
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Save the file to the server
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            // Update the espace with the path to the image
            espace.setPhoto(path.toString());
            espaces.updateEspace(espace);

            return new ResponseEntity<>("Image uploaded successfully: " + fileName, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Could not upload the image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search")
    public List<EspaceEvenement> searchespace(@RequestParam String nom) {
        return espaces.findByNom(nom);
    }

    @GetMapping("/disponibilite")
    public ResponseEntity<List<EspaceEvenement>> getAvailableSpaces(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<EspaceEvenement> espacesDisponibles = espaces.getEspacesDisponibles(date);
        return ResponseEntity.ok().body(espacesDisponibles);
    }
}
