package tn.esprit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String objectif;
    @Temporal(TemporalType.DATE)
    private Date date;
    private int nbLikes = 0;
    private int nbDislikes = 0;
    private int pointsFidelite = 0;
    private double latitude;
    private double longitude;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "club_user",
            joinColumns = @JoinColumn(name = "club_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "club_evenement",
            joinColumns = @JoinColumn(name = "club_id"),
            inverseJoinColumns = @JoinColumn(name = "evenement_id"))
    private Set<Evenement> evenements = new HashSet<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private Set<Formation> formations = new HashSet<>();



}
