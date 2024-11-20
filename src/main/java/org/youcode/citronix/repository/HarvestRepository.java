package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.enums.Saison;

import java.util.List;
import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
    List<Harvest> findBySaison(Saison saison);
}
