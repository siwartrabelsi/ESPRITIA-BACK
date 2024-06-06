package tn.esprit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.EspaceEvenement;
import tn.esprit.services.IEspaceService;

import java.util.List;

@RestController
@RequestMapping("api/espace")
@CrossOrigin(origins = "http://localhost:4200")
public class EspaceRestController {
    @Autowired
    IEspaceService espaces;

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

}
