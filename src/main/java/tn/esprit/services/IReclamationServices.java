package tn.esprit.services;

import tn.esprit.entities.Reclamation;

import java.util.Set;

public interface IReclamationServices {
    public Reclamation addReclamation(Reclamation rec);
    public Set<Reclamation> displayReclamation();
    public Reclamation updateReclamation(long id, Reclamation rec);
    public void deleteReclamation(long id);
    public Reclamation getReclamationById(Long id);
}
