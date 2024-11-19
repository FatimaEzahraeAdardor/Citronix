package org.youcode.citronix.service;

import org.springframework.data.domain.Page;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.service.dto.FarmSearchDto;

import java.util.List;
import java.util.UUID;

public interface FarmService {
    Farm save(Farm farm);
    Farm update(Farm farm);
    Farm findById(UUID id);
    void delete(UUID id);
    List<Farm> findAll();
    Page<Farm> findFarmsWithPaginated(int page, int size);
    List<Farm> search(FarmSearchDto searchDto);
    List<Farm> getFarmCheck(List<Farm> farms);

}
