package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.web.vm.field.FieldResponseVm;
import org.youcode.citronix.service.dto.FieldDto;

@Mapper(componentModel = "spring")
public interface FieldVmMapper {
    Field toField(FieldDto fieldDto);
    FieldDto toFieldDt(Field field);
    FieldResponseVm toResponseVM(Field field);

}