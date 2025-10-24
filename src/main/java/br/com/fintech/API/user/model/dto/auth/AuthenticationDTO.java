package br.com.fintech.API.user.model.dto.auth;

public record AuthenticationDTO(
        String email,
        String password
) {
}
