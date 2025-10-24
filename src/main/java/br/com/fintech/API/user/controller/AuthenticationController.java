package br.com.fintech.API.user.controller;

import br.com.fintech.API.account.model.DTO.AccountResponseDTO;
import br.com.fintech.API.infra.security.TokenService;
import br.com.fintech.API.user.model.User;
import br.com.fintech.API.user.model.dto.auth.AuthenticationDTO;
import br.com.fintech.API.user.model.dto.user.CreateUserRequest;
import br.com.fintech.API.user.model.dto.auth.LoginResponseDTO;
import br.com.fintech.API.user.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.fintech.API.infra.config.APIPaths.AUTH;

@RestController
@RequestMapping(AUTH)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var auth = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.email().trim().toLowerCase(),
                            data.password()
                    )
            );
            User user = (User) auth.getPrincipal();
            var token = tokenService.generateToken(user);
            List<AccountResponseDTO> accounts = Optional.ofNullable(user.getAccounts())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(a -> new AccountResponseDTO(a.getId(), a.getName()))
                    .toList();
            LoginResponseDTO response = new LoginResponseDTO(token, user.toUserResponse(), accounts);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha incorretos");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid CreateUserRequest data) {
        authService.register(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
