package org.youcode.citronix.service.impl;

import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.repository.SaleRepository;
import org.youcode.citronix.service.HarvestService;
import org.youcode.citronix.service.SaleService;
import org.youcode.citronix.service.dto.SaleDto;

import java.util.List;
import java.util.UUID;
@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final HarvestService harvestService;
    public SaleServiceImpl(SaleRepository saleRepository, HarvestService harvestService) {
        this.saleRepository = saleRepository;
        this.harvestService = harvestService;

    }
    @Override
    public SaleDto save(Sale sale) {
        Harvest harvest = harvestService.findById(sale.getHarvest().getId()); // Ensure the harvest is retrieved

        if (sale.getSaleDate().isBefore(harvest.getDate())) {
            throw new IllegalArgumentException("Sale date cannot be earlier than the harvest date.");
        }
        double revenue = sale.getUnit_price() * harvest.getTotal_quantity();
        SaleDto saleDto = new SaleDto(
                sale.getSaleDate(),
                sale.getUnit_price(),
                sale.getClient_name(),
                sale.getHarvest().getId(),
                revenue
        );
        sale.setHarvest(harvest);
        saleRepository.save(sale);
        return saleDto;
    }


    @Override
    public Sale findById(UUID id) {
        return saleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Cannot find sale with id " + id));
    }

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }
}
