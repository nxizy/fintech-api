package br.com.fintech.API.wallet.controller;

import br.com.fintech.API.wallet.model.dto.WalletResponseDTO;
import br.com.fintech.API.wallet.model.dto.WalletTransactionRequestDTO;
import br.com.fintech.API.wallet.model.dto.WalletTransactionResponseDTO;
import br.com.fintech.API.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.UUID;

import static br.com.fintech.API.infra.config.APIPaths.ACCOUNT;

@RestController
@RequestMapping(ACCOUNT)
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;


    //Endpoint 3.1: Consultar saldo e movimentações
    @GetMapping("/{id}/wallet")
    public ResponseEntity<WalletResponseDTO> getWallet(@PathVariable String id) {
        WalletResponseDTO walletData = walletService.getWalletDetails(id);
        return ResponseEntity.ok(walletData);
    }

    //Endpoint 3.2: Cadastrar movimentação
    @PostMapping("/{id}/wallet/transactions")
    public ResponseEntity<WalletTransactionResponseDTO> createTransaction(
            @PathVariable String id,
            @RequestBody WalletTransactionRequestDTO transactionRequest) {

        WalletTransactionResponseDTO response = walletService.createTransaction(id, transactionRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}