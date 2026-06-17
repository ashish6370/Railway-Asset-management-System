package com.railway.assetvault.repository;
import com.railway.assetvault.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {}