package org.youcode.citronix.service;

import org.springframework.data.domain.Page;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.web.vm.field.FieldVm;

import java.util.List;
import java.util.UUID;

public interface FieldService {
    Field save(Field field);
    Field findById(UUID id);
    List<Field> findAll();
    Field update(UUID id, Field field);
    void delete(UUID id);
    Page<Field> findFieldsWithPaginated(int page, int size);
    void deleteByFarmId(UUID farmId);

}
