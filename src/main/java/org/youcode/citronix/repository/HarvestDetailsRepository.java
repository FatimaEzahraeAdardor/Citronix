package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.citronix.domain.HarvestDetails;

import java.util.List;
import java.util.UUID;

public interface HarvestDetailsRepository extends JpaRepository<HarvestDetails, UUID> {
    List<HarvestDetails> findByTreeId(UUID treeId);
    void deleteByTreeId(UUID treeId);
}
