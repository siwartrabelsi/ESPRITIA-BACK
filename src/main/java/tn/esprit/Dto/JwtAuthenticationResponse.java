package tn.esprit.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.entities.User;
@Data
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private User user;
}
