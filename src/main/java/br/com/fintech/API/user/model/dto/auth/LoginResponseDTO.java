package br.com.fintech.API.user.model.dto.auth;

import br.com.fintech.API.account.model.DTO.AccountResponseDTO;
import br.com.fintech.API.user.model.dto.user.UserResponse;

import java.util.List;

public record LoginResponseDTO(
        String token,
        UserResponse user,
        List<AccountResponseDTO> accounts
) {
}
