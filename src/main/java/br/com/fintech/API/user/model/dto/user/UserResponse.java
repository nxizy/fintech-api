package br.com.fintech.API.user.model.dto.user;

import br.com.fintech.API.user.model.enums.InvestorLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "User Response Object")
public class UserResponse {
    @Schema(description = "user's name", example = "John Doe")
    private String name;

    @Schema(description = "user's document", example = "123.456.789-00 or 12.345.678/0001-09")
    private String document;

    @Schema(description = "user's phone number", example = "+55 (12) 99999-9999")
    private String phoneNumber;

    @Schema(description = "user's birth date", example = "2025-08-01")
    private LocalDate birthDate;

    @Schema(description = "user's email", example = "johndoe@gmail.com")
    private String email;

    @Schema(description = "user's investor level", example = "AVANCADO")
    private InvestorLevel investorLevel;

}
