package tn.esprit.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.Covoiturage;
import tn.esprit.services.ICovoiturageServices;

import java.util.List;

@RestController
@RequestMapping("/api/covoiturages")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CovoiturageController {

    private final ICovoiturageServices covoiturageServices;

    @PostMapping
    public void addCovoiturage(@RequestBody Covoiturage covoiturage) {
        covoiturageServices.addCovoiturage(1L, covoiturage);
    }

    @GetMapping("/{id}")
    public Covoiturage getCovoiturageById(@PathVariable Long id) {
        return covoiturageServices.getCovoiturageById(1L, id);
    }

    @GetMapping
    public List<Covoiturage> getAllCovoiturages() {
        return covoiturageServices.getAllCovoiturages(1L);
    }

    @PutMapping("/{id}")
    public void updateCovoiturage(@PathVariable Long id, @RequestBody Covoiturage covoiturage) {
        if (covoiturageServices.getCovoiturageById(1L, id) != null) {
            covoiturage.setId(id);
            covoiturageServices.updateCovoiturage(1L, covoiturage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCovoiturage(@PathVariable Long id) {
        covoiturageServices.deleteCovoiturage(id, 1L);
        return ResponseEntity.noContent().build();
    }
}
