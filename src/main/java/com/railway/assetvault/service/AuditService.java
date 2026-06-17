package com.railway.assetvault.service;
import com.railway.assetvault.entity.*;
import com.railway.assetvault.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class AuditService {
    @Autowired private AuditSessionRepository sessionRepo;
    @Autowired private AuditItemRepository itemRepo;
    @Autowired private AssetRepository assetRepo;
    
    public List<AuditSession> getAllSessions() { return sessionRepo.findAll(); }
    public AuditSession createSession(AuditSession session) { return sessionRepo.save(session); }
    
    @Transactional
    public AuditItem verifyAsset(Long sessionId, Long assetId, String status, String notes) {
        AuditSession session = sessionRepo.findById(sessionId).orElseThrow();
        Asset asset = assetRepo.findById(assetId).orElseThrow();
        AuditItem item = new AuditItem();
        item.setAuditSession(session);
        item.setAsset(asset);
        item.setStatus(status);
        item.setNotes(notes);
        return itemRepo.save(item);
    }
}