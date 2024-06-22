package tn.esprit.services;

import tn.esprit.entities.EspaceEvenement;

import java.util.List;

public interface IEspaceService {
    public EspaceEvenement addEspace(EspaceEvenement espace);
    public EspaceEvenement updateEspace(EspaceEvenement espace);
    public EspaceEvenement getEspaceById(Long id);
    public List<EspaceEvenement> getAllEspace();
    public void deleteEspace(Long id);
}
