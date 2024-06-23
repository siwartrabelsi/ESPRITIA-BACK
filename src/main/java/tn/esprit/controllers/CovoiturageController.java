package tn.esprit.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.Covoiturage;
import tn.esprit.services.ICovoiturageServices;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/covoiturages")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@AllArgsConstructor
public class CovoiturageController {

    private final ICovoiturageServices covoiturageServices;

    @PostMapping
    public void addCovoiturage(@PathVariable Long userId, @RequestBody Covoiturage covoiturage) {
        covoiturageServices.addCovoiturage(userId, covoiturage);
    }

    @GetMapping("/{id}")
    public Covoiturage getCovoiturageById(@PathVariable Long userId, @PathVariable Long id) {
        return covoiturageServices.getCovoiturageById(userId, id);
    }

    @GetMapping
    public List<Covoiturage> getAllCovoiturages(@PathVariable Long userId) {
        return covoiturageServices.getAllCovoiturages(userId);
    }

    @PutMapping("/{id}")
    public void updateCovoiturage(@PathVariable Long userId, @PathVariable Long id, @RequestBody Covoiturage covoiturage) {
        if (covoiturageServices.getCovoiturageById(userId, id) != null) {
            covoiturage.setId(id);
            covoiturageServices.updateCovoiturage(userId, covoiturage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCovoiturage(@PathVariable Long userId, @PathVariable Long id) {
        covoiturageServices.deleteCovoiturage(id, userId);
        return ResponseEntity.noContent().build();
    }
}
