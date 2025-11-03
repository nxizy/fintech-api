package br.com.fintech.API.investments.model;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.assets.model.Asset;
import br.com.fintech.API.assets.model.dto.AssetResponse;
import br.com.fintech.API.investments.model.DTO.InvestmentResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "investments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "investment_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "purchase_price",nullable = false)
    private Double purchasePrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public InvestmentResponseDTO toInvestmentResponse() {
        return InvestmentResponseDTO.builder()
                .id(id)
                .assetName(asset.getName())
                .assetType(asset.getAssetType())
                .amount(amount)
                .purchasePrice(purchasePrice)
                .createdAt(createdAt)
                .build();
    }
}
