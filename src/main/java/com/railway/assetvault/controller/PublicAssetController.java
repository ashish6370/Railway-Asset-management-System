package com.railway.assetvault.controller;

import com.railway.assetvault.entity.Asset;
import com.railway.assetvault.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/public/assets")
public class PublicAssetController {

    @Autowired
    private AssetRepository assetRepository;

    @GetMapping("/{assetId}")
    public ResponseEntity<Map<String, Object>> getPublicAsset(@PathVariable String assetId) {
        Asset asset = assetRepository.findByAssetId(assetId).orElse(null);
        if (asset == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", asset.getId());
        response.put("assetId", asset.getAssetId());
        response.put("assetCode", asset.getAssetId()); // Duplicate since assetCode is assetId
        response.put("assetName", asset.getName());
        response.put("department", asset.getDepartment() != null ? asset.getDepartment().getName() : null);
        response.put("location", asset.getLocation());
        response.put("purchaseDate", asset.getPurchaseDate());
        response.put("purchaseLocation", asset.getPurchaseLocation());
        response.put("warrantyExpiryDate", asset.getWarrantyExpiry());
        response.put("status", asset.getStatus());
        response.put("qrCodePath", asset.getQrCodeData());

        return ResponseEntity.ok(response);
    }
}
