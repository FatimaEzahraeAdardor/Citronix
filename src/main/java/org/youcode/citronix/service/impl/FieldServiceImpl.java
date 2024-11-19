package org.youcode.citronix.service.impl;

import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.repository.FieldRepository;
import org.youcode.citronix.service.FieldService;
import org.youcode.citronix.web.exception.InvalidObjectException;

import java.util.List;
import java.util.UUID;
@Service
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FarmServiceImpl farmServiceImpl;
    public FieldServiceImpl(FieldRepository fieldRepository, FarmServiceImpl farmServiceImpl) {
        this.fieldRepository = fieldRepository;
        this.farmServiceImpl = farmServiceImpl;
    }
    @Override
    public Field save(Field field) {
        if (field == null) {
            throw new InvalidObjectException("Field object cannot be null.");
        }
        if (field.getFarm() == null || field.getFarm().getId() == null) {
            throw new InvalidObjectException("Farm object or its ID cannot be null.");
        }
        Farm farm = farmServiceImpl.findById(field.getFarm().getId()) ;
        validateField(farm, field);
        field.setFarm(farm);
        farm.getFields().add(field);
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
    public Field update(Field field) {
        Field fieldToUpdate = findById(field.getId());
        fieldToUpdate.setArea(field.getArea());
        return fieldRepository.save(fieldToUpdate);
    }

    @Override
    public void delete(UUID id) {
        Field field = findById(id);
        fieldRepository.delete(field);

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
