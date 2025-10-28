package br.com.fintech.API.wallet.model.dto;

import br.com.fintech.API.wallet.model.enums.OperationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OperationDTO(
        UUID operation_id,
        OperationType type,
        BigDecimal amount,
        LocalDateTime created_at
) {}