package com.railway.assetvault.repository;
import com.railway.assetvault.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {
    List<MaintenanceLog> findByAssetId(Long assetId);
    List<MaintenanceLog> findByRequestedById(Long userId);
    List<MaintenanceLog> findByStatus(String status);
    long countByStatus(String status);
}
