package com.railway.assetvault.controller;

import com.railway.assetvault.dto.request.AssignmentRequest;
import com.railway.assetvault.dto.response.AssignmentDTO;
import com.railway.assetvault.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public List<AssignmentDTO> getAll() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/asset/{assetId}")
    public List<AssignmentDTO> getByAssetId(@PathVariable Long assetId) {
        return assignmentService.getAssignmentsByAssetId(assetId);
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignAsset(@RequestBody AssignmentRequest request) {
        try {
            AssignmentDTO dto = assignmentService.assignAsset(request);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<?> returnAsset(@PathVariable Long id) {
        try {
            AssignmentDTO dto = assignmentService.returnAsset(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
