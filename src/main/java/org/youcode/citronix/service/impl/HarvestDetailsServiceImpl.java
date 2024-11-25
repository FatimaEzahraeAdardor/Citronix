package org.youcode.citronix.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.HarvestDetails;
import org.youcode.citronix.repository.HarvestDetailsRepository;
import org.youcode.citronix.service.HarvestDetailsService;

import java.util.List;
import java.util.UUID;

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

    @Override
    public HarvestDetails update(HarvestDetails harvestDetails) {
        HarvestDetails exestingHarvestDetails = harvestDetailsRepository.findById(harvestDetails.getId()).get();
        exestingHarvestDetails.setHarvest(harvestDetails.getHarvest());
        exestingHarvestDetails.setTree(harvestDetails.getTree());
        exestingHarvestDetails.setQuantity(harvestDetails.getQuantity());
        return harvestDetailsRepository.save(exestingHarvestDetails);


    }

    @Override
    public void delete(HarvestDetails harvestDetails) {
        HarvestDetails exestingHarvestDetails = harvestDetailsRepository.findById(harvestDetails.getId()).get();
        harvestDetailsRepository.delete(exestingHarvestDetails);
    }
    @Override
    public List<HarvestDetails> findByTreeId(UUID treeId) {
        return harvestDetailsRepository.findByTreeId(treeId);
    }

    @Override
    @Transactional
    public void deleteByTreeId(UUID treeId) {
        harvestDetailsRepository.deleteByTreeId(treeId);
    }
}
