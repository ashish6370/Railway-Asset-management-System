package com.railway.assetvault.service;

import com.railway.assetvault.dto.request.AssignmentRequest;
import com.railway.assetvault.dto.response.AssignmentDTO;
import com.railway.assetvault.entity.Asset;
import com.railway.assetvault.entity.AssetAssignment;
import com.railway.assetvault.entity.User;
import com.railway.assetvault.exception.ResourceNotFoundException;
import com.railway.assetvault.repository.AssetAssignmentRepository;
import com.railway.assetvault.repository.AssetRepository;
import com.railway.assetvault.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentService {

    @Autowired private AssetAssignmentRepository assignmentRepository;
    @Autowired private AssetRepository assetRepository;
    @Autowired private UserRepository userRepository;

    public List<AssignmentDTO> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(AssignmentDTO::new)
                .collect(Collectors.toList());
    }

    public List<AssignmentDTO> getAssignmentsByAssetId(Long assetId) {
        return assignmentRepository.findByAssetId(assetId).stream()
                .map(AssignmentDTO::new)
                .collect(Collectors.toList());
    }

    public AssignmentDTO assignAsset(AssignmentRequest request) {
        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));
        
        User employee = userRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (!"AVAILABLE".equalsIgnoreCase(asset.getStatus())) {
            throw new RuntimeException("Asset is currently " + asset.getStatus() + " and cannot be assigned. It must be manually returned first.");
        }

        // Create new assignment
        AssetAssignment assignment = new AssetAssignment();
        assignment.setAsset(asset);
        assignment.setUser(employee);
        assignment.setAssignedDate(request.getAssignedDate() != null ? request.getAssignedDate() : LocalDateTime.now());
        assignment.setStatus("ACTIVE");
        
        assignment = assignmentRepository.save(assignment);

        // Update Asset
        asset.setStatus("ASSIGNED");
        asset.setAssignedUser(employee);
        assetRepository.save(asset);

        return new AssignmentDTO(assignment);
    }

    public AssignmentDTO returnAsset(Long assignmentId) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        if (!"ACTIVE".equalsIgnoreCase(assignment.getStatus())) {
            throw new RuntimeException("This assignment is already returned or inactive.");
        }

        // Return the assignment
        assignment.setStatus("RETURNED");
        assignment.setReturnDate(LocalDateTime.now());
        assignment = assignmentRepository.save(assignment);

        // Update the Asset back to available
        Asset asset = assignment.getAsset();
        if (asset != null) {
            asset.setStatus("AVAILABLE");
            asset.setAssignedUser(null);
            assetRepository.save(asset);
        }

        return new AssignmentDTO(assignment);
    }
}
