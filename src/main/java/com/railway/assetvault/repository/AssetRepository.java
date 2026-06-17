package com.railway.assetvault.repository;

import com.railway.assetvault.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {
    Optional<Asset> findByAssetId(String assetId);
    
    long countByStatus(String status);
    
    @Query("SELECT a.category.name as category, SUM(a.purchaseCost) as totalValue FROM Asset a GROUP BY a.category.name")
    List<Map<String, Object>> sumValueByCategory();
    
    @Query("SELECT SUM(a.purchaseCost) FROM Asset a WHERE MONTH(a.createdAt) = MONTH(CURRENT_DATE) AND YEAR(a.createdAt) = YEAR(CURRENT_DATE)")
    Double sumProcurementCostThisMonth();
    
    @Query("SELECT a FROM Asset a WHERE a.warrantyExpiry BETWEEN CURRENT_DATE AND :futureDate")
    List<Asset> findAssetsExpiringBefore(@Param("futureDate") LocalDate futureDate);
}
