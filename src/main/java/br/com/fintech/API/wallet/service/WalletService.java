package br.com.fintech.API.wallet.service;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.account.repository.AccountRepository;
import br.com.fintech.API.infra.exceptions.BadRequestException;
import br.com.fintech.API.infra.exceptions.InsufficientFundsException;
import br.com.fintech.API.infra.exceptions.ResourceNotFoundException;
import br.com.fintech.API.wallet.model.Operation;
import br.com.fintech.API.wallet.model.enums.OperationType;
import br.com.fintech.API.wallet.model.dto.*;
import br.com.fintech.API.wallet.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @Autowired
    public WalletService(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    //Endpoint 3.1: GET /accounts/{id}/wallet
    @Transactional(readOnly = true)
    public WalletResponseDTO getWalletDetails(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        List<Operation> operations = operationRepository.findByAccount_AccountIdOrderByCreatedAtDesc(accountId);

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
    public WalletTransactionResponseDTO createTransaction(UUID accountId, WalletTransactionRequestDTO request) {
        // Validações
        if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor (amount) deve ser positivo.");
        }
        if (request.type() == null) {
            throw new BadRequestException("O tipo (type) é obrigatório.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        // Lógica de Saldo
        BigDecimal newBalance;
        if (request.type() == OperationType.DEPOSIT) {
            newBalance = account.getBalance().add(request.amount());
        } else if (request.type() == OperationType.WITHDRAWAL) {
            if (account.getBalance().compareTo(request.amount()) < 0) {
                throw new InsufficientFundsException("Saldo insuficiente");
            }
            newBalance = account.getBalance().subtract(request.amount());
        } else {
            throw new BadRequestException("Tipo de movimentação inválido");
        }

        account.setBalance(newBalance);
        accountRepository.save(account);

        // Cria e salva a operação
        Operation newOperation = new Operation();
        newOperation.setAccount(account);
        newOperation.setType(request.type());
        newOperation.setAmount(request.amount());
        Operation savedOperation = operationRepository.save(newOperation);

        // Retorna DTO de resposta
        return new WalletTransactionResponseDTO(
                savedOperation.getOperationId(),
                savedOperation.getType(),
                savedOperation.getAmount(),
                savedOperation.getCreatedAt(),
                newBalance
        );
    }
}