package org.youcode.citronix.service;

import org.youcode.citronix.domain.Field;
import org.youcode.citronix.service.dto.FieldDto;

import java.util.List;
import java.util.UUID;

public interface FieldService {
    Field save(FieldDto fieldDto);
    Field findById(UUID id);
    List<Field> findAll();
    Field update(UUID id, FieldDto fieldDto);
    void delete(UUID id);
}
