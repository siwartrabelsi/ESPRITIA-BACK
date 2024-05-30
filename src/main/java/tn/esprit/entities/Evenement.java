package tn.esprit.entities;

import lombok.*;

import javax.persistence.*;
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

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisateur_id")
    private User organisateur;

    private String statut;
    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL)
    private Set<EspaceEvenement> espaces = new HashSet<>();
    @ManyToMany
    private Set<Club> clubs = new HashSet<>();
}
