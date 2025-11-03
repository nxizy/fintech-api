package br.com.fintech.API.account.repository;

import br.com.fintech.API.account.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Page<Account> findByUserId(String userId, Pageable pageable);

    Optional<Account> existsByName(String email);

}
