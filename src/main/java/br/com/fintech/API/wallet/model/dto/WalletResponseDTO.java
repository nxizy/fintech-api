package br.com.fintech.API.wallet.model.dto;

import java.math.BigDecimal;
import java.util.List;

public record WalletResponseDTO(
        BigDecimal balance,
        List<OperationDTO> operations
) {}