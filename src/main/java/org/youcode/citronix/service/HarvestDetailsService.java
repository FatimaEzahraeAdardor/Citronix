package org.youcode.citronix.service;

import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetails;

import java.util.List;
import java.util.UUID;

public interface HarvestDetailsService {
    HarvestDetails save(HarvestDetails harvestDetails);
    HarvestDetails update(HarvestDetails harvestDetails);
    void delete(HarvestDetails harvestDetails);
    List<HarvestDetails> findByTreeId(UUID treeId);
    void deleteByTreeId(UUID treeId);

}
