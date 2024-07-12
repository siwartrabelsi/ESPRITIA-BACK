package tn.esprit.Dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.entities.IRole;

@Data
@NoArgsConstructor
public class SignupRequest {

    private Long id;

    @NotBlank(message = "Nom is required")
    private String nom;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Mot de passe is required")
    private String motDePasse;

    @NotNull(message = "Role is required")
    private IRole role;
    @NotBlank(message = "Phone is required")
    private String phone;
}
