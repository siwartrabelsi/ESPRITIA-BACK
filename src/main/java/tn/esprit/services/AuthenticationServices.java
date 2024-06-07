package tn.esprit.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.Dto.SignupRequest;
import tn.esprit.entities.User;
import tn.esprit.repositories.UserRepository;

@Service
public class AuthenticationServices implements IAuthenticationServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
}
