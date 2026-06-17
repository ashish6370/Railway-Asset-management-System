package com.railway.assetvault.repository;

import com.railway.assetvault.entity.ProcurementRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProcurementRequestRepository extends JpaRepository<ProcurementRequest, Long> {
    List<ProcurementRequest> findByRequestedById(Long userId);
    List<ProcurementRequest> findByStatus(String status);
    long countByStatus(String status);
}
