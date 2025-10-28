package br.com.fintech.API.wallet.model.dto; // Caminho atualizado

import br.com.fintech.API.wallet.model.enums.OperationType; // Import do novo caminho do Enum
import java.math.BigDecimal;

public record WalletTransactionRequestDTO(
        OperationType type,
        BigDecimal amount
) {}