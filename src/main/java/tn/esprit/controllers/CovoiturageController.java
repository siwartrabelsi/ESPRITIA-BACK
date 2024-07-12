package tn.esprit.controllers;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.Covoiturage;
import tn.esprit.entities.IStatutCovoiturage;
import tn.esprit.services.ICovoiturageServices;

import java.util.List;
import java.sql.Date;
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


    @GetMapping("/search")
    public ResponseEntity<List<Covoiturage>> searchCovoiturages(@RequestParam(required = false) String fumeur,
                                                                @RequestParam(required = false) Date  dateDepart,
                                                                @RequestParam(required = false) String lieuDepart,
                                                                @RequestParam(required = false) String destination) {
        List<Covoiturage> covoiturages = covoiturageServices.searchCovoiturages(fumeur, dateDepart, lieuDepart, destination);
        return ResponseEntity.ok(covoiturages);
    }

    @PostMapping("/sendReservationEmail")
    public void sendReservationEmail(@RequestBody Covoiturage covoiturage) {
        covoiturageServices.sendReservationEmail(covoiturage);
    }




}
