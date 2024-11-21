package org.youcode.citronix.service;

import org.springframework.data.domain.Page;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetails;
import org.youcode.citronix.domain.enums.Saison;

import java.util.List;
import java.util.UUID;

public interface HarvestService {
    Harvest save(UUID fieldId, Harvest harvest);
    Harvest findById(UUID id);
    List<Harvest> findAll();
    List<Harvest> findHarvestsBySeason(Saison season);
    Page<Harvest> findHarvestPaginated(int page, int size);
    Harvest update(UUID fieldId ,Harvest harvest);
    void delete(UUID id);


}
