package tn.esprit.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Evenement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String affiche;

    private Double rating;
    private int capacite;
    private int nbrParticipant ;
    private String siteweb ;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisateur_id")
    private User organisateur;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="Membre_id")
    private User membre;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "evenement_participants",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();

    private String statut;
    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL)
    private Set<EspaceEvenement> espaces = new HashSet<>();
    @ManyToMany
    private Set<Club> clubs= new HashSet<>();
}
