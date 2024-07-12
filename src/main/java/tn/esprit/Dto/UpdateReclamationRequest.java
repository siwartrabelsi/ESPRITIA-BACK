package tn.esprit.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.entities.ReclamationStatus;

@Data
@NoArgsConstructor
public class UpdateReclamationRequest {
        @NotBlank(message = "Response should be not empty")
        private String reponse;

        @NotBlank(message = "select Status")
        private ReclamationStatus selectedStatus;
}