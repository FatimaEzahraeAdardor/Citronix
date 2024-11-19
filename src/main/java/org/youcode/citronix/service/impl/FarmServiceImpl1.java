package org.youcode.citronix.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.repository.FarmRepository;
import org.youcode.citronix.repository.FieldRepository;
import org.youcode.citronix.service.FarmService;
import org.youcode.citronix.service.dto.FarmSearchDto;
import org.youcode.citronix.web.exception.InvalidObjectException;

import java.util.List;
import java.util.UUID;
@Service
@Qualifier("impl1")
public class FarmServiceImpl1 implements FarmService {
    private FarmRepository farmRepository;
    private FieldRepository fieldRepository;
    public FarmServiceImpl1(FarmRepository farmRepository, FieldRepository fieldRepository) {
        this.farmRepository = farmRepository;
        this.fieldRepository = fieldRepository;
    }
    @Override
    public Farm save(Farm farm) {
        if (farm.getFields() == null || farm.getFields().isEmpty()) {
            throw new IllegalArgumentException("Farm must have at least one field.");
        }
        Farm savedFarm = farmRepository.save(farm);
        for (Field field : savedFarm.getFields()) {
            field.setFarm(savedFarm);
            fieldRepository.save(field);
        }
        return savedFarm;
    }

    @Override
    public Farm update(Farm farm) {
        return null;
    }

    @Override
    public Farm findById(UUID id) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public List<Farm> findAll() {
        return List.of();
    }

    @Override
    public Page<Farm> findFarmsWithPaginated(int page, int size) {
        return null;
    }

    @Override
    public List<Farm> search(FarmSearchDto searchDto) {
        return List.of();
    }

    @Override
    public List<Farm> getFarmCheck(List<Farm> farms) {
        return List.of();
    }
}
