package br.com.fintech.API.wallet.controller;

import br.com.fintech.API.wallet.model.dto.WalletResponseDTO;
import br.com.fintech.API.wallet.model.dto.WalletTransactionRequestDTO;
import br.com.fintech.API.wallet.model.dto.WalletTransactionResponseDTO;
import br.com.fintech.API.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    //Endpoint 3.1: Consultar saldo e movimentações
    @GetMapping("/{id}/wallet")
    public ResponseEntity<WalletResponseDTO> getWallet(@PathVariable UUID id) {
        WalletResponseDTO walletData = walletService.getWalletDetails(id);
        return ResponseEntity.ok(walletData);
    }

    //Endpoint 3.2: Cadastrar movimentação
    @PostMapping("/{id}/wallet/transactions")
    public ResponseEntity<WalletTransactionResponseDTO> createTransaction(
            @PathVariable UUID id,
            @RequestBody WalletTransactionRequestDTO transactionRequest) {

        WalletTransactionResponseDTO response = walletService.createTransaction(id, transactionRequest);

        // Retorno 201 Created com o header 'Location'
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{operationId}")
                .buildAndExpand(response.operation_id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}