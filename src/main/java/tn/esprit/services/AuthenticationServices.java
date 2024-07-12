package tn.esprit.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.Dto.JwtAuthenticationResponse;
import tn.esprit.Dto.ResetPasswordRequest;
import tn.esprit.Dto.SiginRequest;
import tn.esprit.Dto.SignupRequest;
import tn.esprit.entities.User;
import tn.esprit.repositories.UserRepository;

@Service
public class AuthenticationServices implements IAuthenticationServices {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServices.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;
    @Override
    public User signup(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setMotDePasse(passwordEncoder.encode(signupRequest.getMotDePasse()));
        user.setNom(signupRequest.getNom());
        user.setBanned(false);
        user.setRole(signupRequest.getRole());
        user.setPhone(signupRequest.getPhone());
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
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setUser(user);
        return jwtAuthenticationResponse;
    }
    @Override
    public void sendResetPasswordSMS(String email) {
        Twilio.init(accountSid, authToken);
        logger.info("Attempting to send reset password SMS for email: {}", email);

        Twilio.init(accountSid, authToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    String errorMsg = "User not found with email: " + email;
                    logger.error(errorMsg);
                    return new IllegalArgumentException(errorMsg);
                });
        String resetCode = generateResetCode(); // Implement this method
        user.setResetCode(resetCode);
        userRepository.save(user);
        logger.info(twilioPhoneNumber);
        // Send SMS via Twilio
        Message message = Message.creator(
                new PhoneNumber("+216"+user.getPhone()),
                new PhoneNumber(twilioPhoneNumber),
                "Your reset password code is: " + resetCode
        ).create();

        System.out.println("SMS sent successfully with SID: " + message.getSid());
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetRequest) {
        String email = resetRequest.getEmail();
        String resetCode = resetRequest.getResetCode();
        String newPassword = resetRequest.getNewPassword();

        // Fetch user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        // Check if reset code matches stored code
        if (!resetCode.equals(user.getResetCode())) {
            throw new IllegalArgumentException("Invalid reset code");
        }

        // Update user password
        user.setMotDePasse(passwordEncoder.encode(newPassword));
        user.setResetCode(null);
        userRepository.save(user);
    }

    private String generateResetCode() {
        return String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
    }
}
