package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.service.FieldService;
import org.youcode.citronix.service.impl.FieldServiceImpl;
import org.youcode.citronix.web.vm.field.FieldResponseVm;
import org.youcode.citronix.web.vm.field.FieldVm;
import org.youcode.citronix.web.vm.mapper.FieldVmMapper;
@RestController
@RequestMapping("api/v1/fields")
public class FieldController {
    private final FieldServiceImpl fieldServiceImpl;
    private final FieldVmMapper fieldVmMapper;
    public FieldController(FieldServiceImpl fieldServiceImpl, FieldVmMapper fieldVmMapper) {
        this.fieldServiceImpl = fieldServiceImpl;
        this.fieldVmMapper = fieldVmMapper;
    }

    @PostMapping("save")
    public ResponseEntity<FieldResponseVm> save(@RequestBody @Valid FieldVm fieldVm) {
        Field field = fieldVmMapper.toField(fieldVm);
        fieldServiceImpl.save(field);
        FieldResponseVm fieldResponseVm = fieldVmMapper.toResponseVM(field);
        return new ResponseEntity<>(fieldResponseVm, HttpStatus.CREATED);
    }
}
