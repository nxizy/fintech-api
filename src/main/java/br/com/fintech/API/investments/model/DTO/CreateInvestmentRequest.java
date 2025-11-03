package br.com.fintech.API.investments.model.DTO;

import br.com.fintech.API.account.model.Account;
import br.com.fintech.API.assets.model.Asset;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateInvestmentRequest {

    @NotBlank
    @JsonProperty("asset_name")
    private String assetName;

    @NotNull
    private Double amount;

    @NotNull
    @JsonProperty("purchase_price")
    private Double purchasePrice;
}
