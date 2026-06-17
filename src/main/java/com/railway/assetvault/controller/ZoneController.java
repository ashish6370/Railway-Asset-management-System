package com.railway.assetvault.controller;

import com.railway.assetvault.dto.request.ZoneRequest;
import com.railway.assetvault.entity.Zone;
import com.railway.assetvault.service.ZoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('EMPLOYEE')")
    public List<Zone> getAllZones() {
        return zoneService.getAllZones();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Zone createZone(@Valid @RequestBody ZoneRequest zoneRequest) {
        return zoneService.createZone(zoneRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Zone updateZone(@PathVariable Long id, @Valid @RequestBody ZoneRequest zoneRequest) {
        return zoneService.updateZone(id, zoneRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.ok().build();
    }
}
