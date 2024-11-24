package org.youcode.citronix.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.repository.FieldRepository;
import org.youcode.citronix.service.FarmService;
import org.youcode.citronix.service.FieldService;
import org.youcode.citronix.web.vm.field.FieldVm;
import org.youcode.citronix.web.exception.InvalidObjectException;
import org.youcode.citronix.web.vm.mapper.FieldVmMapper;

import java.util.List;
import java.util.UUID;
@Service
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FarmService farmService;


    public FieldServiceImpl(FieldRepository fieldRepository, FarmService farmService) {
        this.fieldRepository = fieldRepository;
        this.farmService = farmService;
    }
    @Override
    public Field save(Field field) {
        if (field == null) {
            throw new InvalidObjectException("Field object cannot be null.");
        }
        Farm farm = farmService.findById(field.getFarm().getId()) ;
        validateField(farm, field);
        farm.getFields().add(field);
        field.setFarm(farm);
        return fieldRepository.save(field);
    }

    @Override
    public Field findById(UUID id) {
        return fieldRepository.findById(id).orElseThrow(()->new InvalidObjectException("Field not found"));
    }

    @Override
    public List<Field> findAll() {
        return fieldRepository.findAll();
    }

    @Override
    public Field update(UUID id, Field field) {
        Field existingField = findById(id);
        Farm farm = farmService.findById(field.getFarm().getId()) ;
        existingField.setArea(field.getArea());
        validateField(farm, existingField);
        existingField.setFarm(farm);
        return fieldRepository.save(existingField);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        Field field = findById(id);
        fieldRepository.delete(field);

    }

    @Override
    public Page<Field> findFieldsWithPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return fieldRepository.findAll(pageable);
    }

    @Override
    public void deleteByFarmId(UUID farmId) {
        fieldRepository.deleteByFarmId(farmId);
    }

    private void validateField(Farm farm, Field newField) {
        if (newField.getArea() < 0.1) {
            throw new InvalidObjectException("Area should be greater than 0.1");
        }
        if (newField.getArea() > farm.getArea() * 0.5) {
            throw new InvalidObjectException("Field area cannot exceed 50% of the farm's total area");
        }
        if (farm.getFields().size() > 10){
            throw new InvalidObjectException("Fields cannot exceed 10");
        }
        double totalArea = farm.getFields().stream()
                .mapToDouble(Field::getArea)
                .sum() + newField.getArea();
        if (totalArea >= farm.getArea()) {
            throw new InvalidObjectException("Total field area must be less than the farm's total area");
        }
    }
}