package org.youcode.citronix.service;

import org.springframework.data.domain.Page;
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
    Page<Field> findFieldsWithPaginated(int page, int size);
}
