package br.com.fintech.API.investments.service;

import br.com.fintech.API.investments.model.DTO.CreateInvestmentRequest;
import br.com.fintech.API.investments.model.Investment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvestmentService {

    Investment create(String accountId, CreateInvestmentRequest request);

    Page<Investment> findAllByAccount(String accountId, Pageable pageable);

    void delete(String accountId, String investmentId);
}
