package br.com.fintech.API.assets.model.dto;

import br.com.fintech.API.assets.model.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Asset update request payload")
public class UpdateAssetRequest {

    @Schema(description = "asset's type", example = "CRYPTO")
    @JsonProperty("asset_type")
    private AssetType assetType;

    @Schema(description = "asset's name", example = "Ether")
    private String name;

    @Schema(description = "asset's symbol", example = "ETH")
    private String symbol;

    @Schema(description = "asset's current unit price ", example = "22582.84")
    @JsonProperty("current_price")
    private Double currentPrice;

    @Schema(description = "asset's market cap", example = "507048331030.67")
    @JsonProperty("market_cap")
    private Double marketCap;

    @Schema(description = "asset's market sector", example = "platforms")
    @JsonProperty("market_sector")
    private String marketSector;

}
