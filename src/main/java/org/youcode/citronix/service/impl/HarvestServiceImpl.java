package org.youcode.citronix.service.impl;

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

        // Check if the field already has a harvest for the given season
        if (isFieldAlreadyHarvestedInSeason(fieldId, harvest.getDate())) {
            throw new IllegalArgumentException("Each field can only have one harvest per season.");
        }

        // Determine the season based on the harvest date
        Saison season = findSeason(harvest.getDate());
        harvest.setSaison(season);

        // Retrieve the field by its ID
        Field field = fieldService.findById(fieldId);
        if (field == null) {
            throw new IllegalArgumentException("Field with ID " + fieldId + " not found.");
        }

        // Initialize the total quantity and prepare HarvestDetails
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

        // Set the total quantity for the harvest
        harvest.setTotal_quantity(totalQuantity);

        // Save the harvest and harvest details
        Harvest savedHarvest = harvestRepository.save(harvest);
        for (HarvestDetails harvestDetails : harvestDetailsList) {
            harvestDetails.setHarvest(savedHarvest);
            harvestDetailsService.save(harvestDetails);
        }

        return savedHarvest;
    }

    /**
     * Determines if the field has already been harvested in the same season.
     */
    private boolean isFieldAlreadyHarvestedInSeason(UUID fieldId, LocalDate harvestDate) {
        Saison season = findSeason(harvestDate);
        List<Harvest> harvestsInSeason = harvestRepository.findBySaison(season);

        for (Harvest existingHarvest : harvestsInSeason) {
            for (HarvestDetails details : existingHarvest.getHarvestDetails()) {
                if (details.getTree().getField().getId().equals(fieldId)) {
                    return true; // The field has already been harvested in this season
                }
            }
        }
        return false;
    }

    /**
     * Determines the season based on the harvest date.
     */
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
