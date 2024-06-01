package tn.esprit.services;

import tn.esprit.entities.Role;
import tn.esprit.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServices implements IRoleServices {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Set<Role> displayRoles() {
        return new HashSet<>(roleRepository.findAll());
    }
}
