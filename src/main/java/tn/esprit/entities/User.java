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
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nom;
	private String email;
	private String motDePasse;

	@ManyToMany
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Reclamation> reclamations = new HashSet<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Covoiturage> covoiturages = new HashSet<>();
	@OneToMany(mappedBy = "organisateur", cascade = CascadeType.ALL)
	private Set<Evenement> evenements = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "user_club",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "club_id"))
	private Set<Club> clubs = new HashSet<>();
}
