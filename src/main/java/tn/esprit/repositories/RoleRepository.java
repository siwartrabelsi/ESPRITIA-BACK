package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
