package br.com.fintech.API.assets.model.dto;

import br.com.fintech.API.assets.model.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateAssetRequest {
    @NotBlank
    private String name;

    @NotNull
    @JsonProperty("asset_type")
    private AssetType assetType;

    @NotBlank
    private String symbol;

    @NotNull
    @JsonProperty("current_price")
    private Double currentPrice;

    @JsonProperty("market_cap")
    private Double marketCap;

    @JsonProperty("market_sector")
    private String marketSector;
}
