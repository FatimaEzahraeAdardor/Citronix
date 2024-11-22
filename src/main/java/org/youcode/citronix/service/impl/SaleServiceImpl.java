package org.youcode.citronix.service.impl;

import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.repository.SaleRepository;
import org.youcode.citronix.service.HarvestService;
import org.youcode.citronix.service.SaleService;
import org.youcode.citronix.web.vm.sale.SaleVm;
import org.youcode.citronix.web.exception.InvalidObjectException;
import org.youcode.citronix.web.vm.mapper.SaleVmMapper;

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
    public Sale save(UUID harvestId, Sale sale) {
        Harvest harvest = harvestService.findById(harvestId);
        if(sale.getSaleDate().isBefore(harvest.getDate())){
            throw new IllegalArgumentException("Sale date cannot be earlier than the harvest date.");
        }
        sale.setHarvest(harvest);
        double revenue=sale.getUnit_price() * harvest.getTotal_quantity();
        return saleRepository.save(sale);
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
