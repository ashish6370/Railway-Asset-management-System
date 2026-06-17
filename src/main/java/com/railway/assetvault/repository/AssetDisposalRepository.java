package com.railway.assetvault.repository;
import com.railway.assetvault.entity.AssetDisposal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AssetDisposalRepository extends JpaRepository<AssetDisposal, Long> {
    List<AssetDisposal> findByAssetId(Long assetId);
    List<AssetDisposal> findByStatus(String status);
}
