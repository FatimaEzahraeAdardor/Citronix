package org.youcode.citronix.service;

import org.youcode.citronix.domain.Sale;

import java.util.List;
import java.util.UUID;

public interface SaleService {
    Sale save(UUID harvestId, Sale sale);
    Sale findById(UUID id);
    List<Sale> findAll();
}
