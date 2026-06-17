package com.railway.assetvault.service;
import com.railway.assetvault.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import com.railway.assetvault.repository.MaintenanceLogRepository;
import com.railway.assetvault.repository.ProcurementRequestRepository;
import java.time.LocalDate;

@Service
public class DashboardService {
    @Autowired private AssetRepository assetRepo;
    @Autowired private ProcurementRequestRepository procurementRepo;
    @Autowired private MaintenanceLogRepository maintenanceRepo;

    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalAssets", assetRepo.count());
        metrics.put("availableAssets", assetRepo.countByStatus("AVAILABLE"));
        metrics.put("assignedAssets", assetRepo.countByStatus("ASSIGNED"));
        metrics.put("maintenanceAssets", assetRepo.countByStatus("MAINTENANCE"));
        metrics.put("damagedAssets", assetRepo.countByStatus("DAMAGED"));
        
        metrics.put("valueByCategory", assetRepo.sumValueByCategory());
        
        Double currentMonthCost = assetRepo.sumProcurementCostThisMonth();
        metrics.put("monthlyProcurementCost", currentMonthCost != null ? currentMonthCost : 0.0);
        
        long pendingApprovals = procurementRepo.countByStatus("PENDING_APPROVAL") + maintenanceRepo.countByStatus("PENDING_APPROVAL");
        metrics.put("pendingApprovals", pendingApprovals);
        
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        metrics.put("expiringWarranties", assetRepo.findAssetsExpiringBefore(thirtyDaysFromNow));
        
        return metrics;
    }
}