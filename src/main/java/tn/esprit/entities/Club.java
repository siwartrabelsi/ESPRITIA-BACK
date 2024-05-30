package tn.esprit.entities;

import lombok.*;

import javax.persistence.*;
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

    @ManyToMany(mappedBy = "clubs")
    private Set<User> members = new HashSet<>();

    @ManyToMany(mappedBy = "clubs")
    private Set<Evenement> evenements = new HashSet<>();
}
