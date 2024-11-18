package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;

import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
}
