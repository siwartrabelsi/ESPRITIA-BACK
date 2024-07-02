package tn.esprit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Temporal(TemporalType.DATE)
    private LocalDate datedebut ;
    @Temporal(TemporalType.DATE)
    private LocalDate datefin;
    private String clubOrganisateur;
    private String typeEvenement;
    private int nombreParicipantsAttendus;
    @Temporal(TemporalType.TIME)
    private LocalTime heureDebut;
    @Temporal(TemporalType.TIME)
    private LocalTime heureFin;


    @JsonBackReference
    @ManyToOne
    private EspaceEvenement espace;
    public Long getEspaceId() {
        return espace != null ? espace.getId() : null;
    }
}
