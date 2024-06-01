package tn.esprit.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.Covoiturage;
import tn.esprit.services.ICovoiturageServices;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/covoiturage")
@AllArgsConstructor
public class CovoiturageController {

    //@Autowired
    private ICovoiturageServices covoiturageServices;

    @PostMapping
    public void addCovoiturage(@RequestBody Covoiturage covoiturage) {
        covoiturageServices.addCovoiturage(covoiturage);
    }



    @GetMapping("/{id}")
    public Covoiturage getCovoiturageById(@PathVariable Long id) {
        return covoiturageServices.getCovoiturageById(id);
    }

    @GetMapping
    public List<Covoiturage> getAllCovoiturages() {
        return covoiturageServices.getAllCovoiturages();
    }


    @PutMapping("/{id}")
    public void updateCovoiturage(@PathVariable Long id, @RequestBody Covoiturage covoiturage) {
        Covoiturage existingCovoiturage = covoiturageServices.getCovoiturageById(id);
        if (existingCovoiturage != null) {
            covoiturage.setId(id);
            covoiturageServices.updateCovoiturage(covoiturage);
        }
    }

}
