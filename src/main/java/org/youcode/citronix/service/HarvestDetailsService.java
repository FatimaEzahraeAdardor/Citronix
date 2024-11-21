package org.youcode.citronix.service;

import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetails;

public interface HarvestDetailsService {
    HarvestDetails save(HarvestDetails harvestDetails);
    HarvestDetails update(HarvestDetails harvestDetails);

}
