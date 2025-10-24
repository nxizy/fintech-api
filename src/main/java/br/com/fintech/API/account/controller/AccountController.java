package br.com.fintech.API.account.controller;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.account.model.DTO.AccountResponseDTO;
import br.com.fintech.API.account.service.AccountService;
import br.com.fintech.API.infra.security.SecurityUtils;
import br.com.fintech.API.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.fintech.API.infra.config.APIPaths.ACCOUNT;

@RestController
@RequestMapping(ACCOUNT)
@Tag(name = "Account's Management", description = "Operations for creating, updating and retrieving accounts")
public class AccountController {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private AccountService accountService;

    @GetMapping
    @Operation(summary = "Get accounts by the user of the JWT given")
    public ResponseEntity<Page<AccountResponseDTO>> getAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        User user = securityUtils.getCurrentUser();

        Pageable pageable = PageRequest.of(page, size);
        Page<AccountResponseDTO> accountsPage = accountService
                .getAccountsByUserId(user.getId(), pageable)
                .map(a -> new AccountResponseDTO(a.getId(), a.getName()));

        return ResponseEntity.ok(accountsPage);
    }


}
