package com.railway.assetvault.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "audit_sessions")
public class AuditSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    @ManyToOne @JoinColumn(name = "auditor_id") private User auditor;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public LocalDateTime getStartDate() { return startDate; } public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; } public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public String getStatus() { return status; } public void setStatus(String status) { this.status = status; }
    public User getAuditor() { return auditor; } public void setAuditor(User auditor) { this.auditor = auditor; }
}