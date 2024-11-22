package org.youcode.citronix.service;

import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.service.dto.SaleDto;

import java.util.List;
import java.util.UUID;

public interface SaleService {
    SaleDto save(Sale sale);
    Sale findById(UUID id);
    List<Sale> findAll();
}
