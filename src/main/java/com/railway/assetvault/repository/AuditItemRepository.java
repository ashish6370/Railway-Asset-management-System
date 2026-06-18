package com.railway.assetvault.repository;
import com.railway.assetvault.entity.AuditItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AuditItemRepository extends JpaRepository<AuditItem, Long> {
    List<AuditItem> findByAssetId(Long assetId);
}