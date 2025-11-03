package br.com.fintech.API.wallet.service;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.account.repository.AccountRepository;
import br.com.fintech.API.infra.exceptions.BadRequestException;
import br.com.fintech.API.infra.exceptions.InsufficientFundsException;
import br.com.fintech.API.infra.exceptions.ResourceNotFoundException;
import br.com.fintech.API.user.model.enums.InvestorLevel;
import br.com.fintech.API.wallet.model.Operation;
import br.com.fintech.API.wallet.model.enums.OperationType;
import br.com.fintech.API.wallet.model.dto.*;
import br.com.fintech.API.wallet.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;


    //Endpoint 3.1: GET /accounts/{id}/wallet
    @Transactional(readOnly = true)
    public WalletResponseDTO getWalletDetails(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        List<Operation> operations = operationRepository.findByAccount_IdOrderByCreatedAtDesc(account.getId());

        List<OperationDTO> operationDTOs = operations.stream()
                .map(op -> new OperationDTO(
                        op.getOperationId(),
                        op.getType(),
                        op.getAmount(),
                        op.getCreatedAt()))
                .collect(Collectors.toList());

        return new WalletResponseDTO(account.getBalance(), operationDTOs);
    }

    //Endpoint 3.2: POST /accounts/{id}/wallet/transactions
    @Transactional
    public WalletTransactionResponseDTO createTransaction(String accountId, WalletTransactionRequestDTO request) {
        if (request.amount() == null || request.amount() <= 0.0) {
            throw new BadRequestException("O valor (amount) deve ser positivo.");
        }
        if (request.type() == null) {
            throw new BadRequestException("O tipo (type) é obrigatório.");
        }

        if (Arrays.stream(OperationType.values())
                .noneMatch(type -> type.name().equalsIgnoreCase(String.valueOf(request.type())))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo de movimentação inválido");
        }


        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Conta não encontrada"));

        double newBalance = getNewBalance(request, account);

        account.setBalance(newBalance);
        accountRepository.save(account);

        Operation newOperation = Operation.builder()
                .account(account)
                .amount(request.amount())
                .type(request.type())
                .createdAt(LocalDateTime.now())
                .build();
        Operation savedOperation = operationRepository.save(newOperation);

        return new WalletTransactionResponseDTO(
                savedOperation.getOperationId(),
                savedOperation.getType(),
                savedOperation.getAmount(),
                savedOperation.getCreatedAt(),
                newBalance
        );
    }

    private static double getNewBalance(WalletTransactionRequestDTO request, Account account) {
        double newBalance;
        if (request.type() == OperationType.DEPOSIT) {
            newBalance = account.getBalance() + request.amount();
        } else if (request.type() == OperationType.WITHDRAW) {
            if (account.getBalance() < request.amount()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Saldo insuficiente");
            }
            newBalance = account.getBalance() - request.amount();
        } else {
            throw new BadRequestException("Tipo de movimentação inválido");
        }
        return newBalance;
    }
}