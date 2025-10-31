package br.com.fintech.API.assets.controller;

import br.com.fintech.API.assets.model.Asset;
import br.com.fintech.API.assets.model.dto.AssetResponse;
import br.com.fintech.API.assets.model.dto.CreateAssetRequest;
import br.com.fintech.API.assets.model.dto.UpdateAssetRequest;
import br.com.fintech.API.assets.model.enums.AssetType;
import br.com.fintech.API.assets.service.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static br.com.fintech.API.infra.config.APIPaths.ASSETS;

@RestController
@RequestMapping(ASSETS)
@Tag(name = "Assets Management", description = "Operations for creating, updating and retrieving assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping
    @Operation(summary = "Get assets by asset type")
    public ResponseEntity<Page<AssetResponse>> getAssetsByAssetType(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String asset_type
    ){
        Pageable pageable;
        Page<AssetResponse> assetsPage;
        if (sort != null && !sort.isBlank()) {
            pageable = PageRequest.of(page, size, Sort.by(sort.split(",")));
        } else {
            pageable = PageRequest.of(page, size);
        }

        AssetType assetTypeEnum = null;
        if (asset_type != null && !asset_type.isBlank()) {
            try {
                assetTypeEnum = AssetType.valueOf(asset_type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid asset type: " + asset_type);
            }
        }

        if(asset_type == null){
            assetsPage = assetService
                    .findAll(pageable)
                    .map(Asset::toAssetResponse);
        } else {
            assetsPage = assetService
                    .findByAssetType(assetTypeEnum, pageable)
                    .map(Asset::toAssetResponse);
        }
        return ResponseEntity.ok(assetsPage);
    }

    @PostMapping
    @Operation(summary = "Creating an new asset")
    public ResponseEntity<AssetResponse> createAsset(@RequestBody @Valid CreateAssetRequest request) {
        Asset asset = assetService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(asset.toAssetResponse());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updating an existing asset")
    public ResponseEntity<AssetResponse> updateAsset(@PathVariable String id,@Valid @RequestBody UpdateAssetRequest request) {
        Asset asset = assetService.update(id, request);
        return ResponseEntity.ok(asset.toAssetResponse());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting an existing asset")
    public ResponseEntity<Void> deleteAsset(@PathVariable String id) {
        assetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
