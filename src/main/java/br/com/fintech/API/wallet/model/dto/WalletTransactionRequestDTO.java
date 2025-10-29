package br.com.fintech.API.wallet.model.dto;
import br.com.fintech.API.wallet.model.enums.OperationType;


public record WalletTransactionRequestDTO(
        OperationType type,
        Double amount
) {}