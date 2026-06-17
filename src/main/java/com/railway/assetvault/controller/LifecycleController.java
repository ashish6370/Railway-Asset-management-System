package com.railway.assetvault.controller;

import com.railway.assetvault.dto.request.LifecycleRequests.*;
import com.railway.assetvault.entity.*;
import com.railway.assetvault.repository.*;
import com.railway.assetvault.service.LifecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lifecycles")
public class LifecycleController {
    @Autowired private LifecycleService service;
    @Autowired private AssetAssignmentRepository assignRepo;
    @Autowired private AssetTransferRepository transferRepo;
    @Autowired private MaintenanceLogRepository maintenanceRepo;
    @Autowired private AssetDisposalRepository disposalRepo;

    // Getters for specific asset
    @GetMapping("/assignments/asset/{assetId}")
    public List<AssetAssignment> getAssignments(@PathVariable Long assetId) { return assignRepo.findByAssetId(assetId); }
    @GetMapping("/transfers/asset/{assetId}")
    public List<AssetTransfer> getTransfers(@PathVariable Long assetId) { return transferRepo.findByAssetId(assetId); }
    @GetMapping("/maintenance/asset/{assetId}")
    public List<MaintenanceLog> getMaintenance(@PathVariable Long assetId) { return maintenanceRepo.findByAssetId(assetId); }
    @GetMapping("/disposals/asset/{assetId}")
    public List<AssetDisposal> getDisposals(@PathVariable Long assetId) { return disposalRepo.findByAssetId(assetId); }
    
    // Getters for PENDING approvals
    @GetMapping("/transfers/pending")
    public List<AssetTransfer> getPendingTransfers() { return transferRepo.findByStatus("PENDING"); }
    @GetMapping("/disposals/pending")
    public List<AssetDisposal> getPendingDisposals() { return disposalRepo.findByStatus("PENDING"); }

    // Mutations
    @PostMapping("/assignments")
    public AssetAssignment assign(@RequestBody AssignRequest req) { return service.assignAsset(req); }
    @PostMapping("/assignments/{id}/return")
    public AssetAssignment returnAsset(@PathVariable Long id) { return service.returnAsset(id); }

    @PostMapping("/transfers")
    public AssetTransfer requestTransfer(@RequestBody TransferRequest req) { return service.requestTransfer(req); }
    @PostMapping("/transfers/{id}/approve")
    public AssetTransfer approveTransfer(@PathVariable Long id, @RequestBody ApprovalRequest req) { return service.approveTransfer(id, req); }

    @PostMapping("/maintenance")
    public MaintenanceLog scheduleMaintenance(@RequestBody MaintenanceRequest req) { return service.scheduleMaintenance(req); }
    @PostMapping("/maintenance/{id}/complete")
    public MaintenanceLog completeMaintenance(@PathVariable Long id, @RequestBody CompleteMaintenanceRequest req) { return service.completeMaintenance(id, req); }

    @PostMapping("/disposals")
    public AssetDisposal requestDisposal(@RequestBody DisposalRequest req) { return service.requestDisposal(req); }
    @PostMapping("/disposals/{id}/approve")
    public AssetDisposal approveDisposal(@PathVariable Long id, @RequestBody ApprovalRequest req) { return service.approveDisposal(id, req); }
}
