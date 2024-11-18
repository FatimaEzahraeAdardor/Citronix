package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.Farm;

import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Boolean existsByName(String name);
}
