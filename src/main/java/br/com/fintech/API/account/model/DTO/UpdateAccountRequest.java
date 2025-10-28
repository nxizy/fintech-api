package br.com.fintech.API.account.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Account update request payload")
public class UpdateAccountRequest {
    @Schema(description = "name", example = "personal")
    private String name;
}
