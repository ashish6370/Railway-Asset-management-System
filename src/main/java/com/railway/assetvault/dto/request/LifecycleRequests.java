package com.railway.assetvault.dto.request;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LifecycleRequests {
    public static class AssignRequest {
        public Long assetId;
        public Long userId;
    }
    public static class TransferRequest {
        public Long assetId;
        public Long toDepotId;
        public Long requestedByUserId;
    }
    public static class MaintenanceRequest {
        public Long assetId;
        public String maintenanceType;
        public LocalDate scheduledDate;
        public String description;
    }
    public static class CompleteMaintenanceRequest {
        public BigDecimal cost;
        public String performedBy;
    }
    public static class DisposalRequest {
        public Long assetId;
        public String reason;
        public BigDecimal scrapValue;
        public Long requestedByUserId;
    }
    public static class ApprovalRequest {
        public Long approvedByUserId;
        public boolean approved;
    }
}
