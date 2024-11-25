package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.youcode.citronix.domain.Farm;

import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Boolean existsByName(String name);

}
