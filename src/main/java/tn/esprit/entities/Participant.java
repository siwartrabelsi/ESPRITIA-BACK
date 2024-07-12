package tn.esprit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity // Ajoutez cette annotation pour marquer cette classe comme une entité persistante
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formation_id")
    @JsonBackReference
    private Formation formation;



}
