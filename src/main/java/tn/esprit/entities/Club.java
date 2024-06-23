package tn.esprit.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private String description;
    private int nbLikes = 0;
    private int nbDislikes = 0;

    @ManyToMany(mappedBy = "clubs")
    private Set<User> members = new HashSet<>();

    @ManyToMany
    private Set<Evenement> evenements = new HashSet<>();
}
