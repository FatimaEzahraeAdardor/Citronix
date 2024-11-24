package org.youcode.citronix.service;

import org.springframework.data.domain.Page;
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.service.dto.SaleDto;

import java.util.List;
import java.util.UUID;

public interface SaleService {
    SaleDto save(Sale sale);
    Sale findById(UUID id);
    List<Sale> findAll();
    void delete(UUID id);
    Page<Sale> findSalesWithPaginated(int page, int size);

}
