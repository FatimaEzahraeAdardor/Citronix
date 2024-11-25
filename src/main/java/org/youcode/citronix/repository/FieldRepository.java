package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;

import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {
    void deleteByFarmId(UUID farmId);
}
