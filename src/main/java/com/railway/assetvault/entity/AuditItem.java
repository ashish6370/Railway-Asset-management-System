package com.railway.assetvault.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "audit_items")
public class AuditItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name = "audit_session_id") private AuditSession auditSession;
    @ManyToOne @JoinColumn(name = "asset_id") private Asset asset;
    private String status;
    private String notes;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public AuditSession getAuditSession() { return auditSession; } public void setAuditSession(AuditSession auditSession) { this.auditSession = auditSession; }
    public Asset getAsset() { return asset; } public void setAsset(Asset asset) { this.asset = asset; }
    public String getStatus() { return status; } public void setStatus(String status) { this.status = status; }
    public String getNotes() { return notes; } public void setNotes(String notes) { this.notes = notes; }
}