package br.com.fintech.API.investments.controller;

import br.com.fintech.API.investments.model.DTO.CreateInvestmentRequest;
import br.com.fintech.API.investments.model.DTO.InvestmentResponseDTO;
import br.com.fintech.API.investments.model.Investment;
import br.com.fintech.API.investments.service.InvestmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.fintech.API.infra.config.APIPaths.ACCOUNT;

@RestController
@RequestMapping(ACCOUNT)
@RequiredArgsConstructor
@Tag(name = "Investments management", description = "Operations for creating, deleting and retrieving investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    @GetMapping("/{accountId}/investments")
    @Operation(summary = "Get investments by account")
    public ResponseEntity<Page<InvestmentResponseDTO>> findAllByAccount(
            @PathVariable String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<InvestmentResponseDTO> investments = investmentService.findAllByAccount(accountId, pageable)
                .map(Investment::toInvestmentResponse);
        return ResponseEntity.ok(investments);
    }

    @PostMapping("/{accountId}/investments")
    @Operation(summary = "Create investment for the account")
    public ResponseEntity<InvestmentResponseDTO> createInvestment(
            @PathVariable String accountId,
            @RequestBody CreateInvestmentRequest createInvestmentRequest
    ){
        Investment investment = investmentService.create(accountId, createInvestmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(investment.toInvestmentResponse());
    }

    @DeleteMapping("/{accountId}/investments/{investmentId}")
    public ResponseEntity<Void> deleteInvestment(
            @PathVariable String accountId,
            @PathVariable String investmentId
    ){
        investmentService.delete(accountId, investmentId);
        return ResponseEntity.noContent().build();
    }

}
