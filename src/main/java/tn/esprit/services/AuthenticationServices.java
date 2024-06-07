package tn.esprit.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.Dto.JwtAuthenticationResponse;
import tn.esprit.Dto.SiginRequest;
import tn.esprit.Dto.SignupRequest;
import tn.esprit.entities.User;
import tn.esprit.repositories.UserRepository;

import java.util.HashMap;

@Service
public class AuthenticationServices implements IAuthenticationServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Override
    public User signup(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setMotDePasse(passwordEncoder.encode(signupRequest.getMotDePasse()));
        user.setNom(signupRequest.getNom());
        user.setBanned(false);
        user.setRole(signupRequest.getRole());
        userRepository.save(user);
        return user;
    }
    public JwtAuthenticationResponse sigin(SiginRequest siginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                siginRequest.getEmail(),siginRequest.getMotDePasse()
        ));
        var user = userRepository.findByEmail(siginRequest.getEmail()).orElseThrow(
                ()->new IllegalArgumentException("invalid email or password")
        );
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setUser(user);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }
}
