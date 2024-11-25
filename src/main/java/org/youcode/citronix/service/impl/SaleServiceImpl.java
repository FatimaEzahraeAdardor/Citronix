package org.youcode.citronix.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.domain.Tree;
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
        Harvest harvest = harvestService.findById(sale.getHarvest().getId());

        if (sale.getSaleDate().isBefore(harvest.getDate())) {
            throw new IllegalArgumentException("Sale date cannot be earlier than the harvest date.");
        }
        double sumSaled = saleRepository.findTotalQuantitySoldByHarvestId(sale.getHarvest().getId()).orElse(0.0);
        double quantityLeft = harvest.getTotal_quantity() - sumSaled;
        if (sale.getQuantity()> quantityLeft) {
            throw new IllegalArgumentException("Harvest available : " + quantityLeft + " kg ");
        };
        double revenue = sale.getUnit_price() * sale.getQuantity();
        SaleDto saleDto = new SaleDto(
                sale.getSaleDate(),
                sale.getQuantity(),
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
        return saleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cannot find sale with id " + id));
    }

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        Sale sale = findById(id);
         saleRepository.delete(sale);
    }

    @Override
    public Page<Sale> findSalesWithPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return saleRepository.findAll(pageable);
    }

}
