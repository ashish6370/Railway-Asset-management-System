package com.railway.assetvault.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "asset_disposals")
public class AssetDisposal extends Auditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "asset_id") private Asset asset;
    private LocalDateTime requestDate;
    private LocalDateTime approvalDate;
    private String reason; // DAMAGED, OBSOLETE, SOLD
    private BigDecimal scrapValue;
    private String status; // PENDING, APPROVED, REJECTED
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "requested_by") private User requestedBy;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "approved_by") private User approvedBy;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }
    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
    public LocalDateTime getApprovalDate() { return approvalDate; }
    public void setApprovalDate(LocalDateTime approvalDate) { this.approvalDate = approvalDate; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public BigDecimal getScrapValue() { return scrapValue; }
    public void setScrapValue(BigDecimal scrapValue) { this.scrapValue = scrapValue; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getRequestedBy() { return requestedBy; }
    public void setRequestedBy(User requestedBy) { this.requestedBy = requestedBy; }
    public User getApprovedBy() { return approvedBy; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }
}
