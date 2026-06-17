package com.railway.assetvault.service;

import com.railway.assetvault.dto.request.ZoneRequest;
import com.railway.assetvault.entity.Zone;
import com.railway.assetvault.exception.ResourceNotFoundException;
import com.railway.assetvault.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    public Zone createZone(ZoneRequest request) {
        Zone zone = new Zone();
        zone.setName(request.getName());
        zone.setCode(request.getCode());
        return zoneRepository.save(zone);
    }

    public Zone updateZone(Long id, ZoneRequest request) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id " + id));
        zone.setName(request.getName());
        zone.setCode(request.getCode());
        return zoneRepository.save(zone);
    }

    public void deleteZone(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id " + id));
        zoneRepository.delete(zone);
    }
}
