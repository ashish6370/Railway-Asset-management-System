package com.railway.assetvault.dto.response;

import com.railway.assetvault.entity.AssetAssignment;
import java.time.LocalDateTime;

public class AssignmentDTO {
    public Long id;
    public Long assetId;
    public String assetSerialNumber;
    public String assetName;
    public Long userId;
    public String userName;
    public String userEmployeeId;
    public LocalDateTime assignedDate;
    public LocalDateTime returnDate;
    public String status;

    public AssignmentDTO(AssetAssignment assignment) {
        this.id = assignment.getId();
        this.assignedDate = assignment.getAssignedDate();
        this.returnDate = assignment.getReturnDate();
        this.status = assignment.getStatus();
        
        if (assignment.getAsset() != null) {
            this.assetId = assignment.getAsset().getId();
            this.assetSerialNumber = assignment.getAsset().getSerialNumber();
            this.assetName = assignment.getAsset().getName();
        }
        
        if (assignment.getUser() != null) {
            this.userId = assignment.getUser().getId();
            this.userName = assignment.getUser().getName();
            this.userEmployeeId = assignment.getUser().getEmployeeId();
        }
    }
}
