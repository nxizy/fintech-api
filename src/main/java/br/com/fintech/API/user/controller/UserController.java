package br.com.fintech.API.user.controller;

import br.com.fintech.API.infra.security.SecurityUtils;
import br.com.fintech.API.user.model.User;
import br.com.fintech.API.user.model.dto.user.UpdateUserRequest;
import br.com.fintech.API.user.model.dto.user.UserResponse;
import br.com.fintech.API.user.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.fintech.API.infra.config.APIPaths.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
@Tag(name = "User's management", description = "Operations for updating and deleting users")
public class UserController {

    private final UserService userService;

    private final SecurityUtils securityUtils;

    @PutMapping()
    @Operation(summary = "Updating the user of the JWT given")
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UpdateUserRequest request) {
        User currentUser = securityUtils.getCurrentUser();
        var user = userService.update(currentUser.getId(), request);

        return ResponseEntity.ok(user.toUserResponse());
    }

    @DeleteMapping()
    @Operation(summary = "Deleting the user of the JWT given")
    public ResponseEntity<Void> delete() {
        userService.delete(securityUtils.getCurrentUser().getId());
        return ResponseEntity.noContent().build();
    }
}
