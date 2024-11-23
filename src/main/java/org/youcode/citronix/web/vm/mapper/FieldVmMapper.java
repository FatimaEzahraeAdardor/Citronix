package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.web.vm.field.FieldResponseVm;
import org.youcode.citronix.web.vm.field.FieldVm;

@Mapper(componentModel = "spring" )
public interface FieldVmMapper {
    @Mapping(source = "farmId", target = "farm.id")
    Field toField(FieldVm fieldDto);
    FieldVm toFiedlVm(Field field);
    FieldResponseVm toResponseVM(Field field);

}