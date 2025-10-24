package br.com.fintech.API.user.model.dto.user;

import br.com.fintech.API.user.model.enums.InvestorLevel;
import br.com.fintech.API.user.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateUserRequest{

    @NotBlank
    private String name;

    @NotBlank
    private String document;

    private String phoneNumber;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UserRole userRole;

    private InvestorLevel investorLevel;
}
