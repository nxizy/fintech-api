package br.com.fintech.API.investments.repository;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.investments.model.Investment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, String> {

    Page<Investment> findAllByAccount_Id(String accountId, Pageable pageable);
}
