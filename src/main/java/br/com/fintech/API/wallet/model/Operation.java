package br.com.fintech.API.wallet.model; // Caminho atualizado

import br.com.fintech.API.account.model.Account; // Dependência da pasta 'account'
import br.com.fintech.API.wallet.model.enums.OperationType; // Import do novo caminho do Enum
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_OPERATION")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "operation_id")
    private UUID operationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OperationType type;

    @Column(nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}