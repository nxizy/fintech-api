package br.com.fintech.API.wallet.model.dto; // Caminho atualizado

import br.com.fintech.API.wallet.model.enums.OperationType; // Import do novo caminho do Enum
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletTransactionResponseDTO(
        UUID operation_id,
        OperationType type,
        BigDecimal amount,
        LocalDateTime created_at,
        BigDecimal balance_after
) {}