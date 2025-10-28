package br.com.fintech.API.account.model.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDTO {
    private String id;
    private String name;
    private Double balance;
    private LocalDateTime createdAt;


    public AccountResponseDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
