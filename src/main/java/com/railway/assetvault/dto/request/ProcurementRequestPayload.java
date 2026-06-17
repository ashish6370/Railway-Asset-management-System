package com.railway.assetvault.dto.request;

import java.math.BigDecimal;

public class ProcurementRequestPayload {
    private Long userId;
    private String itemName;
    private BigDecimal estimatedCost;
    private String justification;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public BigDecimal getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; }
    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }
}
