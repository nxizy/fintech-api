package br.com.fintech.API.account.service;


import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.account.model.DTO.CreateAccountRequest;
import br.com.fintech.API.account.model.DTO.UpdateAccountRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Account create(CreateAccountRequest account);

    Account update(String accountId, UpdateAccountRequest account);

    Account findById(String id);

    Page<Account> getAccountsByUserId(String userId, Pageable pageable);

    void delete(String id);
}
