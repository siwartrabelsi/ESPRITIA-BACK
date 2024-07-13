package tn.esprit.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.Log;
import tn.esprit.entities.User;
import tn.esprit.repositories.LogRepository;
import tn.esprit.services.IUserServices;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin/user")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminUserController {
    @Autowired
    private IUserServices userServices;
    @Autowired
    private LogRepository logRepository;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createdUser = userServices.addUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userServices.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<Set<User>> getAllUsers() {
        Set<User> users = userServices.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userServices.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userServices.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/banUnban/{id}")
    public ResponseEntity<User> bannirUnbanUser(@PathVariable Long id) {
        User user = userServices.bannirUnbanUser(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<Log>> getAllLogs() {
        List<Log> logs = logRepository.findAll();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/charts")
    public ResponseEntity<Map<String, Long>> getUsersByRole() {
        Map<String, Long> usersByRole = userServices.getUsersByRole();
        return new ResponseEntity<>(usersByRole, HttpStatus.OK);
    }
}
