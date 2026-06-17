package com.railway.assetvault.service;

import com.railway.assetvault.dto.request.LifecycleRequests.*;
import com.railway.assetvault.entity.*;
import com.railway.assetvault.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LifecycleService {
    @Autowired private AssetRepository assetRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private DepotRepository depotRepo;
    @Autowired private AssetAssignmentRepository assignRepo;
    @Autowired private AssetTransferRepository transferRepo;
    @Autowired private MaintenanceLogRepository maintenanceRepo;
    @Autowired private AssetDisposalRepository disposalRepo;

    // Assignment
    public AssetAssignment assignAsset(AssignRequest req) {
        Asset asset = assetRepo.findById(req.assetId).orElseThrow();
        if (asset.getAssignedUser() != null || "ASSIGNED".equals(asset.getStatus())) {
            throw new RuntimeException("Asset is already assigned to an employee.");
        }
        
        User user = userRepo.findById(req.userId).orElseThrow();
        
        AssetAssignment assignment = new AssetAssignment();
        assignment.setAsset(asset);
        assignment.setUser(user);
        assignment.setAssignedDate(LocalDateTime.now());
        assignment.setStatus("ACTIVE");
        
        asset.setAssignedUser(user);
        asset.setAssignedDate(LocalDate.now());
        asset.setStatus("ASSIGNED");
        assetRepo.save(asset);
        
        return assignRepo.save(assignment);
    }
    
    public AssetAssignment returnAsset(Long assignmentId) {
        AssetAssignment assignment = assignRepo.findById(assignmentId).orElseThrow();
        assignment.setStatus("RETURNED");
        assignment.setReturnDate(LocalDateTime.now());
        
        Asset asset = assignment.getAsset();
        asset.setAssignedUser(null);
        asset.setAssignedDate(null);
        asset.setStatus("AVAILABLE");
        assetRepo.save(asset);
        
        return assignRepo.save(assignment);
    }
    
    // Transfer
    public AssetTransfer requestTransfer(TransferRequest req) {
        Asset asset = assetRepo.findById(req.assetId).orElseThrow();
        Depot toDepot = depotRepo.findById(req.toDepotId).orElseThrow();
        User requester = userRepo.findById(req.requestedByUserId).orElseThrow();
        
        AssetTransfer transfer = new AssetTransfer();
        transfer.setAsset(asset);
        transfer.setFromDepot(asset.getDepot());
        transfer.setToDepot(toDepot);
        transfer.setRequestedBy(requester);
        transfer.setRequestDate(LocalDateTime.now());
        transfer.setStatus("PENDING");
        return transferRepo.save(transfer);
    }

    public AssetTransfer approveTransfer(Long transferId, ApprovalRequest req) {
        AssetTransfer transfer = transferRepo.findById(transferId).orElseThrow();
        User approver = userRepo.findById(req.approvedByUserId).orElseThrow();
        transfer.setApprovedBy(approver);
        transfer.setApprovalDate(LocalDateTime.now());
        
        if(req.approved) {
            transfer.setStatus("APPROVED");
            Asset asset = transfer.getAsset();
            asset.setDepot(transfer.getToDepot());
            assetRepo.save(asset);
        } else {
            transfer.setStatus("REJECTED");
        }
        return transferRepo.save(transfer);
    }

    // Maintenance
    public MaintenanceLog scheduleMaintenance(MaintenanceRequest req) {
        Asset asset = assetRepo.findById(req.assetId).orElseThrow();
        MaintenanceLog log = new MaintenanceLog();
        log.setAsset(asset);
        log.setMaintenanceType(req.maintenanceType);
        log.setScheduledDate(req.scheduledDate);
        log.setDescription(req.description);
        log.setStatus("SCHEDULED");
        
        asset.setStatus("MAINTENANCE");
        assetRepo.save(asset);
        return maintenanceRepo.save(log);
    }

    public MaintenanceLog completeMaintenance(Long logId, CompleteMaintenanceRequest req) {
        MaintenanceLog log = maintenanceRepo.findById(logId).orElseThrow();
        log.setCost(req.cost);
        log.setPerformedBy(req.performedBy);
        log.setCompletionDate(LocalDate.now());
        log.setStatus("COMPLETED");
        
        Asset asset = log.getAsset();
        asset.setStatus("ACTIVE");
        assetRepo.save(asset);
        return maintenanceRepo.save(log);
    }
    
    // Disposal
    public AssetDisposal requestDisposal(DisposalRequest req) {
        Asset asset = assetRepo.findById(req.assetId).orElseThrow();
        User requester = userRepo.findById(req.requestedByUserId).orElseThrow();
        
        AssetDisposal disposal = new AssetDisposal();
        disposal.setAsset(asset);
        disposal.setRequestedBy(requester);
        disposal.setReason(req.reason);
        disposal.setScrapValue(req.scrapValue);
        disposal.setRequestDate(LocalDateTime.now());
        disposal.setStatus("PENDING");
        return disposalRepo.save(disposal);
    }

    public AssetDisposal approveDisposal(Long disposalId, ApprovalRequest req) {
        AssetDisposal disposal = disposalRepo.findById(disposalId).orElseThrow();
        User approver = userRepo.findById(req.approvedByUserId).orElseThrow();
        disposal.setApprovedBy(approver);
        disposal.setApprovalDate(LocalDateTime.now());
        
        if(req.approved) {
            disposal.setStatus("APPROVED");
            Asset asset = disposal.getAsset();
            asset.setStatus("DISPOSED");
            assetRepo.save(asset);
        } else {
            disposal.setStatus("REJECTED");
        }
        return disposalRepo.save(disposal);
    }
}
