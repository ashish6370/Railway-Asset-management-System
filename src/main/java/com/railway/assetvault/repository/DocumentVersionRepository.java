package com.railway.assetvault.repository;
import com.railway.assetvault.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {}