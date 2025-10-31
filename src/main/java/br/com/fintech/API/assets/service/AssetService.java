package br.com.fintech.API.assets.service;

import br.com.fintech.API.assets.model.Asset;
import br.com.fintech.API.assets.model.dto.CreateAssetRequest;
import br.com.fintech.API.assets.model.dto.UpdateAssetRequest;
import br.com.fintech.API.assets.model.enums.AssetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AssetService {

    Asset create(CreateAssetRequest asset);

    Asset update(String id ,UpdateAssetRequest asset);

    Page<Asset> findAll(Pageable pageable);

    Page<Asset> findByAssetType(AssetType assetType, Pageable pageable);

    void delete(String id);

}
