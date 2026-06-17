package com.railway.assetvault.repository;

import com.railway.assetvault.entity.Asset;
import org.springframework.data.jpa.domain.Specification;

public class AssetSpecification {
    public static Specification<Asset> containsName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
    public static Specification<Asset> hasStatus(String status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }
}
