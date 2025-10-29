package br.com.fintech.API.wallet.model;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.wallet.model.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OPERATIONS")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "operation_id", length = 36)
    private UUID operationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OperationType type;

    @Column(nullable = false)
    private Double amount;

    @CreationTimestamp
    @Column(name = "occured_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}