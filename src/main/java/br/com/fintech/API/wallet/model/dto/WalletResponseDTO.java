package br.com.fintech.API.wallet.model.dto;

import java.util.List;

public record WalletResponseDTO(
        Double balance,
        List<OperationDTO> operations
) {}