package br.com.fintech.API.investments.model.DTO;

import br.com.fintech.API.assets.model.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Investment Response Object")
public class InvestmentResponseDTO {

    @Schema(description = "investment's id", example = "af7dcce4-46c3-4db2-ae83-d7ce8edc825d")
    @JsonProperty("investment_id")
    private String id;

    @Schema(description = "asset's name", example = "BBAS3")
    @JsonProperty("asset_name")
    private String assetName;

    @Schema(description = "asset's type", example = "stock")
    @JsonProperty("asset_type")
    private AssetType assetType;

    @Schema(description = "asset's amount acquired", example = "2.0")
    private Double amount;

    @Schema(description = "asset's purchase price when acquired", example = "41.22")
    @JsonProperty("purchase_price")
    private Double purchasePrice;

    @Schema(description = "investment creation date", example = "2025-10-15T15:15:00")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
