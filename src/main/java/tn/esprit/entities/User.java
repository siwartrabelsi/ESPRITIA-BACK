package tn.esprit.entities;

import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nom;
	@Column(unique = true)
	private String email;
	private String motDePasse;
	private boolean banned = false;
	@Enumerated(EnumType.STRING)
	private IRole role;

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return motDePasse;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !banned;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
