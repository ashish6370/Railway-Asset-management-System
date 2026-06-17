package com.railway.assetvault.repository;
import com.railway.assetvault.entity.AssetAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AssetAssignmentRepository extends JpaRepository<AssetAssignment, Long> {
    List<AssetAssignment> findByAssetId(Long assetId);
}
