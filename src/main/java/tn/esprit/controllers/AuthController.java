package tn.esprit.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Dto.SignupRequest;
import tn.esprit.entities.User;
import tn.esprit.services.IAuthenticationServices;

@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private IAuthenticationServices authenticationServices;
    @PostMapping("signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest user) {
        User createdUser = authenticationServices.signup(user);
        return ResponseEntity.ok(createdUser);
    }
}
