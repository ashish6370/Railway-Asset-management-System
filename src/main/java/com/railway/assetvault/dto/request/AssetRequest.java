package com.railway.assetvault.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AssetRequest {
    private String name;
    private Long categoryId;
    private String serialNumber;
    private LocalDate purchaseDate;
    private BigDecimal purchaseCost;
    private String brand;
    private String model;
    private LocalDate warrantyExpiry;
    private String location;
    private String remarks;
    private Long costCenterId;
    private String conditionStatus;
    private Long depotId;
    private String purchaseLocation;
    private String vendorName;
    private LocalDate warrantyStartDate;
    private LocalDate endOfLifeDate;
    private Long departmentId;
    private Long assignedUserId;
    private String maintenanceSchedule;
    private LocalDate lastServiceDate;
    private LocalDate nextServiceDate;
    private String status;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    public BigDecimal getPurchaseCost() { return purchaseCost; }
    public void setPurchaseCost(BigDecimal purchaseCost) { this.purchaseCost = purchaseCost; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public LocalDate getWarrantyExpiry() { return warrantyExpiry; }
    public void setWarrantyExpiry(LocalDate warrantyExpiry) { this.warrantyExpiry = warrantyExpiry; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public Long getCostCenterId() { return costCenterId; }
    public void setCostCenterId(Long costCenterId) { this.costCenterId = costCenterId; }
    public String getConditionStatus() { return conditionStatus; }
    public void setConditionStatus(String conditionStatus) { this.conditionStatus = conditionStatus; }
    public Long getDepotId() { return depotId; }
    public void setDepotId(Long depotId) { this.depotId = depotId; }
    public String getPurchaseLocation() { return purchaseLocation; }
    public void setPurchaseLocation(String purchaseLocation) { this.purchaseLocation = purchaseLocation; }
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public LocalDate getWarrantyStartDate() { return warrantyStartDate; }
    public void setWarrantyStartDate(LocalDate warrantyStartDate) { this.warrantyStartDate = warrantyStartDate; }
    public LocalDate getEndOfLifeDate() { return endOfLifeDate; }
    public void setEndOfLifeDate(LocalDate endOfLifeDate) { this.endOfLifeDate = endOfLifeDate; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public Long getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(Long assignedUserId) { this.assignedUserId = assignedUserId; }
    public String getMaintenanceSchedule() { return maintenanceSchedule; }
    public void setMaintenanceSchedule(String maintenanceSchedule) { this.maintenanceSchedule = maintenanceSchedule; }
    public LocalDate getLastServiceDate() { return lastServiceDate; }
    public void setLastServiceDate(LocalDate lastServiceDate) { this.lastServiceDate = lastServiceDate; }
    public LocalDate getNextServiceDate() { return nextServiceDate; }
    public void setNextServiceDate(LocalDate nextServiceDate) { this.nextServiceDate = nextServiceDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
