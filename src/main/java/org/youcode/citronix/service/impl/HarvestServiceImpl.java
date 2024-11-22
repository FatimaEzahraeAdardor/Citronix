package org.youcode.citronix.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.HarvestDetails;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.domain.enums.Saison;
import org.youcode.citronix.repository.HarvestRepository;
import org.youcode.citronix.service.FieldService;
import org.youcode.citronix.service.HarvestDetailsService;
import org.youcode.citronix.service.HarvestService;
import org.youcode.citronix.web.exception.InvalidObjectException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final FieldService fieldService;
    private final HarvestDetailsService harvestDetailsService;

    public HarvestServiceImpl(HarvestRepository harvestRepository,
                              HarvestDetailsService harvestDetailsService,
                              FieldService fieldService) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailsService = harvestDetailsService;
        this.fieldService = fieldService;
    }

    @Override
    public Harvest save(UUID fieldId, Harvest harvest) {
        if (harvest == null) {
            throw new InvalidObjectException("Harvest object cannot be null.");
        }

        if (isFieldAlreadyHarvestedInSeason(fieldId, harvest.getDate())) {
            throw new IllegalArgumentException("Each field can only have one harvest per season.");
        }

        Saison season = findSeason(harvest.getDate());
        harvest.setSaison(season);

        Field field = fieldService.findById(fieldId);
        if (field == null) {
            throw new IllegalArgumentException("Field with ID " + fieldId + " not found.");
        }

        List<HarvestDetails> harvestDetailsList = new ArrayList<>();
        double totalQuantity = 0;

        for (Tree tree : field.getTrees()) {
            HarvestDetails harvestDetails = new HarvestDetails();
            harvestDetails.setHarvest(harvest);
            harvestDetails.setTree(tree);
            harvestDetails.setQuantity(tree.getProductiviteAnnuelle());
            harvestDetailsList.add(harvestDetails);
            totalQuantity += tree.getProductiviteAnnuelle();
        }

        harvest.setTotal_quantity(totalQuantity);

        Harvest savedHarvest = harvestRepository.save(harvest);
        for (HarvestDetails harvestDetails : harvestDetailsList) {
            harvestDetails.setHarvest(savedHarvest);
            harvestDetailsService.save(harvestDetails);
        }

        return savedHarvest;
    }

    @Override
    public Harvest findById(UUID id) {
        return harvestRepository.findById(id).orElseThrow(()->new InvalidObjectException("Harvest with ID " + id + " not found."));
    }

    @Override
    public List<Harvest> findAll() {
        return harvestRepository.findAll();
    }

    @Override
    public List<Harvest> findHarvestsBySeason(Saison season) {
        return harvestRepository.findBySaison(season);
    }

    @Override
    public Page<Harvest> findHarvestPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return harvestRepository.findAll(pageable);
    }

    @Override
    public Harvest update(UUID fieldId, Harvest harvest) {
        if (harvest == null) {
            throw new InvalidObjectException("Harvest object cannot be null.");
        }
        Harvest existingHarvest = harvestRepository.findById(harvest.getId())
                .orElseThrow(() -> new InvalidObjectException("Harvest with ID " + harvest.getId() + " not found."));

        // Check if the field has already been harvested in the current season
        if (isFieldAlreadyHarvestedInSeason(fieldId, harvest.getDate())) {
            throw new IllegalArgumentException("Each field can only have one harvest per season.");
        }

        // Determine the season based on the new harvest date
        Saison season = findSeason(harvest.getDate());

        Field field = fieldService.findById(fieldId);
        if (field == null) {
            throw new IllegalArgumentException("Field with ID " + fieldId + " not found.");
        }

        existingHarvest.setDate(harvest.getDate());
        existingHarvest.setSaison(season);

        List<HarvestDetails> updatedHarvestDetailsList = new ArrayList<>();
        double totalQuantity = 0;

        for (Tree tree : field.getTrees()) {
            HarvestDetails harvestDetails = new HarvestDetails();
            harvestDetails.setHarvest(existingHarvest);
            harvestDetails.setTree(tree);
            harvestDetails.setQuantity(tree.getProductiviteAnnuelle());
            updatedHarvestDetailsList.add(harvestDetails);
            totalQuantity += tree.getProductiviteAnnuelle();
        }

        existingHarvest.setTotal_quantity(totalQuantity);

        Harvest updatedHarvest = harvestRepository.save(existingHarvest);

        for (HarvestDetails harvestDetails : updatedHarvestDetailsList) {
            harvestDetails.setHarvest(updatedHarvest);
            harvestDetailsService.update(harvestDetails);
        }

        return updatedHarvest;
    }
    @Override
    public void delete(UUID id) {
        Harvest harvest = findById(id);
        harvestRepository.delete(harvest);
    }


    private boolean isFieldAlreadyHarvestedInSeason(UUID fieldId, LocalDate harvestDate) {
        Saison season = findSeason(harvestDate);
        List<Harvest> harvestsInSeason = harvestRepository.findBySaison(season);
        for (Harvest existingHarvest : harvestsInSeason) {
            for (HarvestDetails details : existingHarvest.getHarvestDetails()) {
                if (details.getTree().getField().getId().equals(fieldId)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Saison findSeason(LocalDate harvestDate) {
        int month = harvestDate.getMonthValue();
        if (month >= 3 && month <= 5) {
            return Saison.SPRING;
        } else if (month >= 6 && month <= 8) {
            return Saison.SUMMER;
        } else if (month >= 9 && month <= 11) {
            return Saison.AUTUMN;
        } else {
            return Saison.WINTER;
        }
    }
}