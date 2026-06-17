package com.railway.assetvault.dto.response;

import com.railway.assetvault.entity.MaintenanceLog;
import com.railway.assetvault.entity.ProcurementRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RequestDTO {
    public Long id;
    public String type; // MAINTENANCE, PROCUREMENT
    public String itemName;
    public String assetSerialNumber;
    public String status;
    public String requestedBy;
    public LocalDateTime requestDate;
    public String justification;
    public BigDecimal estimatedCost;

    public RequestDTO(ProcurementRequest pr) {
        this.id = pr.getId();
        this.type = "PROCUREMENT";
        this.itemName = pr.getItemName();
        this.status = pr.getStatus();
        this.requestedBy = pr.getRequestedBy() != null ? pr.getRequestedBy().getName() : "Unknown";
        this.requestDate = pr.getRequestDate();
        this.justification = pr.getJustification();
        this.estimatedCost = pr.getEstimatedCost();
    }

    public RequestDTO(MaintenanceLog ml) {
        this.id = ml.getId();
        this.type = "MAINTENANCE";
        if (ml.getAsset() != null) {
            this.itemName = ml.getAsset().getName();
            this.assetSerialNumber = ml.getAsset().getSerialNumber();
        }
        this.status = ml.getStatus();
        this.requestedBy = ml.getRequestedBy() != null ? ml.getRequestedBy().getName() : "Unknown";
        this.requestDate = ml.getCreatedAt() != null ? ml.getCreatedAt() : LocalDateTime.now();
        this.justification = ml.getDescription();
        this.estimatedCost = ml.getCost();
    }
}
