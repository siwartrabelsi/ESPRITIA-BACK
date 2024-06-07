/*package tn.esprit.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.Role;
import tn.esprit.services.IRoleServices;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleServices roleServices;

    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        Role createdRole = roleServices.addRole(role);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping
    public ResponseEntity<Set<Role>> getAllRoles() {
        Set<Role> roles = roleServices.displayRoles();
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleServices.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
*/