package br.com.fintech.API.account.service;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.account.model.DTO.CreateAccountRequest;
import br.com.fintech.API.account.model.DTO.UpdateAccountRequest;
import br.com.fintech.API.account.repository.AccountRepository;
import br.com.fintech.API.infra.exceptions.InvalidRequestException;
import br.com.fintech.API.infra.security.SecurityUtils;
import br.com.fintech.API.user.model.User;
import br.com.fintech.API.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    private final SecurityUtils securityUtils;

    @Override
    public Account create(String userid, CreateAccountRequest request) {
        if(request.getName() == null || request.getName().isBlank()) throw new InvalidRequestException("Account name is required");
        User user = userRepository.findById(userid).orElseThrow(() -> new AccessDeniedException("User not found"));
        Account account = Account.builder()
                .user(user)
                .name(request.getName())
                .balance(0.0)
                .createdAt(LocalDateTime.now())
                .build();

        return accountRepository.save(account);
    }

    @Override
    public Account update(String accountId, UpdateAccountRequest request) {
        User user = securityUtils.getCurrentUser();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account not found"));
        if(!account.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to update this account");
        }

        account.setName(request.getName());
        return accountRepository.save(account);
    }

    @Override
    public Account findById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account not found"));
    }

    @Override
    public Page<Account> getAccountsByUserId(String userId, Pageable pageable) {
        return accountRepository.findByUserId(userId, pageable);
    }

    @Override
    public void delete(String id) {
        accountRepository.deleteById(id);
    }
}
