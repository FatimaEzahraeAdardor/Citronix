package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Harvest;

import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
}
