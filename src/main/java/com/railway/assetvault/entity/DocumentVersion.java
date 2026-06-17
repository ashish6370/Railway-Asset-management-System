package com.railway.assetvault.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "document_versions")
public class DocumentVersion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name = "asset_id") private Asset asset;
    private String filePath;
    private Integer version;
    private LocalDateTime uploadedAt = LocalDateTime.now();
    @ManyToOne @JoinColumn(name = "uploaded_by") private User uploadedBy;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Asset getAsset() { return asset; } public void setAsset(Asset asset) { this.asset = asset; }
    public String getFilePath() { return filePath; } public void setFilePath(String filePath) { this.filePath = filePath; }
    public Integer getVersion() { return version; } public void setVersion(Integer version) { this.version = version; }
    public LocalDateTime getUploadedAt() { return uploadedAt; } public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
    public User getUploadedBy() { return uploadedBy; } public void setUploadedBy(User uploadedBy) { this.uploadedBy = uploadedBy; }
}