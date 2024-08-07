package tn.esprit.services;

import tn.esprit.entities.EspaceEvenement;

import java.time.LocalDate;
import java.util.List;

public interface IEspaceService {
    public EspaceEvenement addEspace(EspaceEvenement espace);
    public EspaceEvenement updateEspace(EspaceEvenement espace);
    public EspaceEvenement getEspaceById(Long id);
    public List<EspaceEvenement> getAllEspace();
    public void deleteEspace(Long id);
    List<EspaceEvenement> findByNom(String nom);
    List<EspaceEvenement> getEspacesDisponibles(LocalDate date);
}
