package com.railway.assetvault.controller;

import com.railway.assetvault.dto.request.AssetRequest;
import com.railway.assetvault.entity.Asset;
import com.railway.assetvault.service.AssetService;
import com.railway.assetvault.service.ExcelService;
import com.railway.assetvault.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/assets")
public class AssetController {
    @Autowired private AssetService service;
    @Autowired private FileStorageService fileService;
    @Autowired private ExcelService excelService;

    @GetMapping
    public java.util.Map<String, Object> getPaginatedAssets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Asset> pageData = service.getAssetsPaginated(name, status, pageable);
        
        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("content", pageData.getContent());
        response.put("totalElements", pageData.getTotalElements());
        response.put("totalPages", pageData.getTotalPages());
        
        return response;
    }
    
    @GetMapping("/{id}")
    public Asset getAsset(@PathVariable Long id) {
        return service.getAssetById(id);
    }

    @PostMapping
    public Asset createAsset(@RequestBody AssetRequest request) {
        return service.createAsset(request);
    }

    @PostMapping("/{id}/upload")
    public Asset uploadAssetFiles(@PathVariable Long id, 
                                  @RequestParam(value="image", required=false) MultipartFile image,
                                  @RequestParam(value="document", required=false) MultipartFile document) {
        String imagePath = image != null ? fileService.storeFile(image) : null;
        String docPath = document != null ? fileService.storeFile(document) : null;
        return service.updateAssetFiles(id, imagePath, docPath);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportExcel() {
        InputStreamResource file = new InputStreamResource(excelService.assetsToExcel(service.getAll()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=assets.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @PostMapping("/{id}/regenerate-qr")
    public Asset regenerateQr(@PathVariable Long id) {
        return service.regenerateQrCode(id);
    }
}
