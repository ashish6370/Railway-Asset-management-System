package com.railway.assetvault.controller;
import com.railway.assetvault.entity.*;
import com.railway.assetvault.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/audits") @CrossOrigin
public class AuditController {
    @Autowired private AuditService auditService;
    @GetMapping public List<AuditSession> getAll() { return auditService.getAllSessions(); }
    @PostMapping public AuditSession create(@RequestBody AuditSession s) { return auditService.createSession(s); }
    @PostMapping("/{sessionId}/verify") public AuditItem verify(@PathVariable Long sessionId, @RequestParam Long assetId, @RequestParam String status, @RequestParam(required=false) String notes) {
        return auditService.verifyAsset(sessionId, assetId, status, notes);
    }
}