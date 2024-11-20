package org.youcode.citronix.service;

import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetails;

import java.util.UUID;

public interface HarvestService {
    Harvest save(UUID fieldId, Harvest harvest);
}
