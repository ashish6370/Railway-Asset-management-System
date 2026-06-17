package com.railway.assetvault.repository;
import com.railway.assetvault.entity.AuditSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuditSessionRepository extends JpaRepository<AuditSession, Long> {}