package br.com.fintech.API.investments.service;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.account.repository.AccountRepository;
import br.com.fintech.API.assets.model.Asset;
import br.com.fintech.API.assets.repository.AssetRepository;
import br.com.fintech.API.investments.model.DTO.CreateInvestmentRequest;
import br.com.fintech.API.investments.model.Investment;
import br.com.fintech.API.investments.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final AccountRepository accountRepository;
    private final AssetRepository assetRepository;

    @Override
    public Investment create(String accountId, CreateInvestmentRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));

        Asset asset = assetRepository.findByName(request.getAssetName());
        if(asset == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Asset not found");
        }
        if(account.getBalance() < request.getPurchasePrice()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Insufficient funds");
        }
        if(request.getPurchasePrice() <= 0 || request.getAmount() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Purchase price and Amount must be greater than 0");
        }

        account.setBalance(account.getBalance() - request.getPurchasePrice());
        accountRepository.save(account);
        Investment investment = Investment.builder()
                .asset(asset)
                .account(account)
                .amount(request.getAmount())
                .purchasePrice(request.getPurchasePrice())
                .createdAt(LocalDateTime.now())
                .build();
        return investmentRepository.save(investment);
    }

    @Override
    public Page<Investment> findAllByAccount(String accountId, Pageable pageable) {
        return investmentRepository.findAllByAccount_Id(accountId, pageable);
    }

    @Override
    public void delete(String accountId, String investmentId) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Investment not found"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        if(!investment.getAccount().getId().equals(account.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Investment does not belong to this account");
        }
        //Devolvendo o dinheiro do investimento
        account.setBalance(account.getBalance() + investment.getPurchasePrice());
        accountRepository.save(account);
        investmentRepository.delete(investment);
    }
}
