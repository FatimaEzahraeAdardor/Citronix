package org.youcode.citronix.service.impl;

import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.HarvestDetails;
import org.youcode.citronix.repository.HarvestDetailsRepository;
import org.youcode.citronix.service.HarvestDetailsService;

@Service
public class HarvestDetailsServiceImpl implements HarvestDetailsService {
    private final HarvestDetailsRepository harvestDetailsRepository;
    public HarvestDetailsServiceImpl(HarvestDetailsRepository harvestDetailsRepository) {
        this.harvestDetailsRepository = harvestDetailsRepository;
    }
    @Override
    public HarvestDetails save(HarvestDetails harvestDetails) {
        return harvestDetailsRepository.save(harvestDetails);
    }
}
