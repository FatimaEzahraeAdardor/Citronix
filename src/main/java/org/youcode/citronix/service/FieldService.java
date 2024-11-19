package org.youcode.citronix.service;

import org.youcode.citronix.domain.Field;

import java.util.List;
import java.util.UUID;

public interface FieldService {
    Field save(Field field);
    Field findById(UUID id);
    List<Field> findAll();
    Field update(Field field);
    void delete(UUID id);
}
