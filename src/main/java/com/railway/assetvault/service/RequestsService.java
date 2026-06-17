package com.railway.assetvault.service;

import com.railway.assetvault.dto.request.MaintenanceRequestPayload;
import com.railway.assetvault.dto.request.ProcurementRequestPayload;
import com.railway.assetvault.dto.response.RequestDTO;
import com.railway.assetvault.entity.Asset;
import com.railway.assetvault.entity.MaintenanceLog;
import com.railway.assetvault.entity.ProcurementRequest;
import com.railway.assetvault.entity.User;
import com.railway.assetvault.exception.ResourceNotFoundException;
import com.railway.assetvault.repository.AssetRepository;
import com.railway.assetvault.repository.MaintenanceLogRepository;
import com.railway.assetvault.repository.ProcurementRequestRepository;
import com.railway.assetvault.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestsService {

    @Autowired private ProcurementRequestRepository procurementRepo;
    @Autowired private MaintenanceLogRepository maintenanceRepo;
    @Autowired private UserRepository userRepository;
    @Autowired private AssetRepository assetRepository;

    public RequestDTO submitProcurementRequest(ProcurementRequestPayload payload) {
        User user = userRepository.findById(payload.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ProcurementRequest req = new ProcurementRequest();
        req.setRequestedBy(user);
        req.setItemName(payload.getItemName());
        req.setEstimatedCost(payload.getEstimatedCost());
        req.setJustification(payload.getJustification());
        req.setStatus("PENDING_APPROVAL");
        req.setRequestDate(LocalDateTime.now());

        return new RequestDTO(procurementRepo.save(req));
    }

    public RequestDTO submitMaintenanceRequest(MaintenanceRequestPayload payload) {
        User user = userRepository.findById(payload.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Asset asset = assetRepository.findById(payload.getAssetId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));

        MaintenanceLog log = new MaintenanceLog();
        log.setRequestedBy(user);
        log.setAsset(asset);
        log.setMaintenanceType(payload.getMaintenanceType());
        log.setDescription(payload.getDescription());
        log.setStatus("PENDING_APPROVAL");

        return new RequestDTO(maintenanceRepo.save(log));
    }

    public List<RequestDTO> getMyRequests(Long userId) {
        List<RequestDTO> requests = new ArrayList<>();
        
        procurementRepo.findByRequestedById(userId).forEach(pr -> requests.add(new RequestDTO(pr)));
        maintenanceRepo.findByRequestedById(userId).forEach(ml -> requests.add(new RequestDTO(ml)));
        
        requests.sort(Comparator.comparing(r -> r.requestDate, Comparator.nullsLast(Comparator.reverseOrder())));
        return requests;
    }

    public List<RequestDTO> getPendingRequests() {
        List<RequestDTO> requests = new ArrayList<>();
        
        procurementRepo.findByStatus("PENDING_APPROVAL").forEach(pr -> requests.add(new RequestDTO(pr)));
        maintenanceRepo.findByStatus("PENDING_APPROVAL").forEach(ml -> requests.add(new RequestDTO(ml)));
        
        requests.sort(Comparator.comparing(r -> r.requestDate, Comparator.nullsLast(Comparator.reverseOrder())));
        return requests;
    }

    public RequestDTO processProcurementRequest(Long id, boolean approve) {
        ProcurementRequest req = procurementRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Procurement Request not found"));
        
        req.setStatus(approve ? "APPROVED" : "REJECTED");
        req.setResponseDate(LocalDateTime.now());
        return new RequestDTO(procurementRepo.save(req));
    }

    public RequestDTO processMaintenanceRequest(Long id, boolean approve) {
        MaintenanceLog log = maintenanceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance Request not found"));
        
        log.setStatus(approve ? "APPROVED" : "REJECTED");
        return new RequestDTO(maintenanceRepo.save(log));
    }
}
