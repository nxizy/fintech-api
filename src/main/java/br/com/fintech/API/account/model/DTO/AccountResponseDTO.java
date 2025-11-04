package br.com.fintech.API.account.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Account Response Object")
public class AccountResponseDTO {

    @Schema(description = "account's id", example = "6e54e570-2d4e-11ef-bf4b-123456789abc")
    private String id;

    @Schema(description = "account's name", example = "Pessoal")
    private String name;

    @Schema(description = "account's balance", example = "0.0")
    private Double balance;

    @Schema(description = "account's creation date", example = "2025-10-28T09:57:10.15959")
    private LocalDateTime createdAt;


    public AccountResponseDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
