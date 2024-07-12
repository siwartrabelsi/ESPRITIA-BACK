package tn.esprit.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Dto.JwtAuthenticationResponse;
import tn.esprit.Dto.ResetPasswordRequest;
import tn.esprit.Dto.SiginRequest;
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
    public ResponseEntity<?> signup(@RequestBody SignupRequest user) {
        try {
            User createdUser = authenticationServices.signup(user);
            return ResponseEntity.ok(createdUser);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("sigin")
    public ResponseEntity<?> sigin(@RequestBody SiginRequest user) {
        try {
            JwtAuthenticationResponse loggedUser = authenticationServices.sigin(user);
            return ResponseEntity.ok(loggedUser);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/sendResetPasswordSMS")
    public ResponseEntity<?> sendResetPasswordSMS(@RequestBody String email) {
        try {
            authenticationServices.sendResetPasswordSMS(email);
            return ResponseEntity.ok("Reset password SMS sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetRequest) {
        try {
            authenticationServices.resetPassword(resetRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
