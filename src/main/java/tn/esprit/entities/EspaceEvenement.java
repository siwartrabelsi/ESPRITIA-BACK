package tn.esprit.entities;


import jakarta.persistence.*;
import lombok.*;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import tn.esprit.entities.Enums.TypeEquipement;


import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EspaceEvenement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private int capacite;
    private String description;
    private String adresse;
    private String photo ;
    @Enumerated(EnumType.STRING)
    private TypeEquipement equipement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evenement_id")
    private Evenement evenement;

    @JsonManagedReference
    @OneToMany(mappedBy = "espace")
    private Set<Reservation> reservations;
}
