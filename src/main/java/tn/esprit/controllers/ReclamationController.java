package tn.esprit.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.IReclamationServices;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/reclamation")
public class ReclamationController {

    @Autowired
    private IReclamationServices reclamationServices;

    @PostMapping
    public ResponseEntity<Reclamation> addReclamation(@RequestBody Reclamation reclamation) {
        Reclamation createdReclamation = reclamationServices.addReclamation(reclamation);
        return ResponseEntity.ok(createdReclamation);
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

    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> updateReclamation(@PathVariable Long id, @RequestBody Reclamation reclamation) {
        Reclamation updatedReclamation = reclamationServices.updateReclamation(id, reclamation);
        if (updatedReclamation != null) {
            return ResponseEntity.ok(updatedReclamation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        reclamationServices.deleteReclamation(id);
        return ResponseEntity.noContent().build();
    }
}
