package com.railway.assetvault.controller;

import com.railway.assetvault.dto.request.MaintenanceRequestPayload;
import com.railway.assetvault.dto.request.ProcurementRequestPayload;
import com.railway.assetvault.dto.response.RequestDTO;
import com.railway.assetvault.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestsController {

    @Autowired
    private RequestsService requestsService;

    @GetMapping("/my-requests")
    public List<RequestDTO> getMyRequests(@RequestParam Long userId) {
        return requestsService.getMyRequests(userId);
    }

    @GetMapping("/pending")
    public List<RequestDTO> getPendingRequests() {
        return requestsService.getPendingRequests();
    }

    @PostMapping("/maintenance")
    public ResponseEntity<?> submitMaintenance(@RequestBody MaintenanceRequestPayload payload) {
        try {
            return ResponseEntity.ok(requestsService.submitMaintenanceRequest(payload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/procurement")
    public ResponseEntity<?> submitProcurement(@RequestBody ProcurementRequestPayload payload) {
        try {
            return ResponseEntity.ok(requestsService.submitProcurementRequest(payload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/procurement/{id}/process")
    public ResponseEntity<?> processProcurement(@PathVariable Long id, @RequestParam boolean approve) {
        try {
            return ResponseEntity.ok(requestsService.processProcurementRequest(id, approve));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/maintenance/{id}/process")
    public ResponseEntity<?> processMaintenance(@PathVariable Long id, @RequestParam boolean approve) {
        try {
            return ResponseEntity.ok(requestsService.processMaintenanceRequest(id, approve));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
