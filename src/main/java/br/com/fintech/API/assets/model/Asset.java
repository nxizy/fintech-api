package br.com.fintech.API.assets.model;

import br.com.fintech.API.assets.model.dto.AssetResponse;
import br.com.fintech.API.assets.model.dto.UpdateAssetRequest;
import br.com.fintech.API.assets.model.enums.AssetType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import static br.com.fintech.API.infra.utils.SanitizerUtils.sanitize;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "asset_id")
    private String id;

    @Column(length = 10, nullable = false, unique = true)
    private String symbol;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type",nullable = false)
    private AssetType assetType;

    @Column(name = "current_price", nullable = false)
    private Double currentPrice;

    @Column(name = "market_sector")
    private String marketSector;

    @Column(name = "market_cap")
    private Double marketCap;


    public AssetResponse toAssetResponse() {
        return AssetResponse.builder()
                .id(this.id)
                .assetType(this.assetType)
                .name(this.name)
                .symbol(this.symbol)
                .currentPrice(this.currentPrice)
                .marketCap(this.marketCap)
                .marketSector(this.marketSector)
                .build();
    }

    public void merge(UpdateAssetRequest request) {
        if(request.getAssetType() != null) this.assetType = request.getAssetType();
        if(request.getName() != null) this.name = request.getName();
        if(request.getSymbol() != null) this.symbol = request.getSymbol();
        if(request.getCurrentPrice() != null) this.currentPrice = request.getCurrentPrice();
        if(request.getMarketCap() != null) this.marketCap = request.getMarketCap();
        if(request.getMarketSector() != null) this.marketSector = request.getMarketSector();
    }
}
