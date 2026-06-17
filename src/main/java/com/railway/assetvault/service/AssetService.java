package com.railway.assetvault.service;

import com.railway.assetvault.dto.request.AssetRequest;
import com.railway.assetvault.entity.Asset;
import com.railway.assetvault.entity.Category;
import com.railway.assetvault.entity.Department;
import com.railway.assetvault.entity.User;
import com.railway.assetvault.exception.ResourceNotFoundException;
import com.railway.assetvault.repository.AssetRepository;
import com.railway.assetvault.repository.CategoryRepository;
import com.railway.assetvault.repository.DepartmentRepository;
import com.railway.assetvault.repository.UserRepository;
import com.railway.assetvault.repository.AssetSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AssetService {

    @Autowired private AssetRepository assetRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private QrCodeGeneratorService qrService;
    @Autowired private FileStorageService fileService;

    @Value("${app.public-url}")
    private String publicUrl;

    public Page<Asset> getAssetsPaginated(String name, String status, Pageable pageable) {
        Specification<Asset> spec = Specification.where(AssetSpecification.containsName(name))
                                                 .and(AssetSpecification.hasStatus(status));
        return assetRepository.findAll(spec, pageable);
    }
    
    public List<Asset> getAll() { return assetRepository.findAll(); }
    
    public Asset getAssetById(Long id) {
        return assetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Asset not found"));
    }

    public Asset createAsset(AssetRequest request) {
        Asset asset = new Asset();
        String generatedId = "AST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        asset.setAssetId(generatedId);
        asset.setName(request.getName());
        asset.setSerialNumber(request.getSerialNumber());
        asset.setPurchaseDate(request.getPurchaseDate());
        asset.setPurchaseCost(request.getPurchaseCost());
        asset.setBrand(request.getBrand());
        asset.setModel(request.getModel());
        asset.setWarrantyExpiry(request.getWarrantyExpiry());
        asset.setLocation(request.getLocation());
        asset.setRemarks(request.getRemarks());
        asset.setCostCenterId(request.getCostCenterId());
        asset.setConditionStatus(request.getConditionStatus() != null ? request.getConditionStatus() : "GOOD");
        
        asset.setPurchaseLocation(request.getPurchaseLocation());
        asset.setVendorName(request.getVendorName());
        asset.setWarrantyStartDate(request.getWarrantyStartDate());
        asset.setEndOfLifeDate(request.getEndOfLifeDate());
        asset.setMaintenanceSchedule(request.getMaintenanceSchedule());
        asset.setLastServiceDate(request.getLastServiceDate());
        asset.setNextServiceDate(request.getNextServiceDate());
        
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            asset.setStatus(request.getStatus());
        } else {
            asset.setStatus("AVAILABLE");
        }

        if (request.getCategoryId() != null) {
            Category cat = categoryRepository.findById(request.getCategoryId()).orElse(null);
            asset.setCategory(cat);
        }

        if (request.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(request.getDepartmentId()).orElse(null);
            asset.setDepartment(dept);
        }

        if (request.getAssignedUserId() != null) {
            User user = userRepository.findById(request.getAssignedUserId()).orElse(null);
            asset.setAssignedUser(user);
            if (user != null) {
                asset.setAssignedDate(LocalDate.now());
            }
        }



        String url = publicUrl + "/public/assets/" + generatedId;
        asset.setQrCodeData(qrService.generateQrCodeImage(url, generatedId));

        return assetRepository.save(asset);
    }
    
    public Asset updateAssetFiles(Long assetId, String imagePath, String docPath) {
        Asset asset = getAssetById(assetId);
        if(imagePath != null) asset.setImagePath(imagePath);
        if(docPath != null) asset.setDocumentPath(docPath);
        return assetRepository.save(asset);
    }

    public Asset regenerateQrCode(Long id) {
        Asset asset = getAssetById(id);
        String url = publicUrl + "/public/assets/" + asset.getAssetId();
        asset.setQrCodeData(qrService.generateQrCodeImage(url, asset.getAssetId()));
        return assetRepository.save(asset);
    }
}
