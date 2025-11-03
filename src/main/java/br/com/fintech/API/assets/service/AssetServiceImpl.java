package br.com.fintech.API.assets.service;

import br.com.fintech.API.assets.model.Asset;
import br.com.fintech.API.assets.model.dto.CreateAssetRequest;
import br.com.fintech.API.assets.model.dto.UpdateAssetRequest;
import br.com.fintech.API.assets.model.enums.AssetType;
import br.com.fintech.API.assets.repository.AssetRepository;
import br.com.fintech.API.infra.exceptions.NotFoundException;
import br.com.fintech.API.user.model.enums.InvestorLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;

    @Override
    public Asset create(CreateAssetRequest request) {
        if (Arrays.stream(AssetType.values())
                .noneMatch(type -> type.name().equalsIgnoreCase(String.valueOf(request.getAssetType())))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid asset type");
        }

        Asset asset = Asset.builder()
                .name(request.getName())
                .symbol(request.getSymbol())
                .assetType(request.getAssetType())
                .currentPrice(request.getCurrentPrice())
                .marketSector(request.getMarketSector())
                .marketCap(request.getMarketCap())
                .build();
        return assetRepository.save(asset);
    }

    @Override
    public Asset update(String id, UpdateAssetRequest request) {
        Asset assetToUpdate = assetRepository.findById(id).orElseThrow(() -> new NotFoundException("Asset not found"));
        assetToUpdate.merge(request);
        return assetRepository.save(assetToUpdate);
    }

    @Override
    public Page<Asset> findAll(Pageable pageable) {
        return assetRepository.findAll(pageable);
    }

    @Override
    public Page<Asset> findByAssetType(AssetType assetType, Pageable pageable) {
        return assetRepository.findByAssetType(assetType, pageable);
    }

    @Override
    public void delete(String id) {
        assetRepository.deleteById(id);
    }

}
