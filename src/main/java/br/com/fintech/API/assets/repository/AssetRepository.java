package br.com.fintech.API.assets.repository;

import br.com.fintech.API.assets.model.Asset;
import br.com.fintech.API.assets.model.enums.AssetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, String> {
    Page<Asset> findByAssetType(AssetType assetType, Pageable pageable);

    Asset findByName(String name);
}
