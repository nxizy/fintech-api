package br.com.fintech.API.assets.model.dto;

import br.com.fintech.API.assets.model.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Asset Response Object")
public class AssetResponse {

    @Schema(description = "asset's id", example = "3e67f35b-9169-47f2-b51e-f166345026c2")
    private String id;

    @Schema(description = "asset's type", example = "CRYPTO")
    @JsonProperty("asset_type")
    private AssetType assetType;

    @Schema(description = "asset's name", example = "Bitcoin")
    private String name;

    @Schema(description = "asset's symbol", example = "BTC")
    private String symbol;

    @Schema(description = "asset's current unit price ", example = "618540.95")
    @JsonProperty("current_price")
    private Double currentPrice;

    @Schema(description = "asset's market cap", example = "2159769775906.57")
    @JsonProperty("market_cap")
    private Double marketCap;

    @Schema(description = "asset's market sector", example = "payments")
    @JsonProperty("market_sector")
    private String marketSector;

}
