package com.railway.assetvault.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "activity_logs")
public class ActivityLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name = "user_id") private User user;
    private String action;
    private String entityName;
    private Long entityId;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String details;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public User getUser() { return user; } public void setUser(User user) { this.user = user; }
    public String getAction() { return action; } public void setAction(String action) { this.action = action; }
    public String getEntityName() { return entityName; } public void setEntityName(String entityName) { this.entityName = entityName; }
    public Long getEntityId() { return entityId; } public void setEntityId(Long entityId) { this.entityId = entityId; }
    public LocalDateTime getTimestamp() { return timestamp; } public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getDetails() { return details; } public void setDetails(String details) { this.details = details; }
}