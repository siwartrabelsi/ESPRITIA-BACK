package tn.esprit.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.function.Function;

public interface JWTService {
    String generateToken(UserDetails userDetails);
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
