package com.railway.assetvault.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "asset_transfers")
public class AssetTransfer extends Auditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "asset_id") private Asset asset;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "from_depot_id") private Depot fromDepot;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "to_depot_id") private Depot toDepot;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "requested_by") private User requestedBy;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "approved_by") private User approvedBy;
    private LocalDateTime requestDate;
    private LocalDateTime approvalDate;
    private String status; // PENDING, APPROVED, REJECTED
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }
    public Depot getFromDepot() { return fromDepot; }
    public void setFromDepot(Depot fromDepot) { this.fromDepot = fromDepot; }
    public Depot getToDepot() { return toDepot; }
    public void setToDepot(Depot toDepot) { this.toDepot = toDepot; }
    public User getRequestedBy() { return requestedBy; }
    public void setRequestedBy(User requestedBy) { this.requestedBy = requestedBy; }
    public User getApprovedBy() { return approvedBy; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }
    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
    public LocalDateTime getApprovalDate() { return approvalDate; }
    public void setApprovalDate(LocalDateTime approvalDate) { this.approvalDate = approvalDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
