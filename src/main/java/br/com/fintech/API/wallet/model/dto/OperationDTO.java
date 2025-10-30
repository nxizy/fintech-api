package br.com.fintech.API.wallet.model.dto;

import br.com.fintech.API.wallet.model.enums.OperationType;
import java.time.LocalDateTime;
import java.util.UUID;

public record OperationDTO(
        UUID operation_id,
        OperationType type,
        Double amount,
        LocalDateTime created_at
) {}