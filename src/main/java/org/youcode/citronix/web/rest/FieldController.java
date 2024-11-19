package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.service.impl.FieldServiceImpl;
import org.youcode.citronix.web.vm.field.FieldResponseVm;
import org.youcode.citronix.service.dto.FieldDto;
import org.youcode.citronix.web.vm.mapper.FieldVmMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<FieldResponseVm> save(@RequestBody @Valid FieldDto fieldDto) {
        fieldServiceImpl.save(fieldDto);
        Field field = fieldVmMapper.toField(fieldDto);
        FieldResponseVm fieldResponseVm = fieldVmMapper.toResponseVM(field);
        return new ResponseEntity<>(fieldResponseVm, HttpStatus.CREATED);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<FieldResponseVm> update(@PathVariable UUID id, @RequestBody @Valid FieldDto fieldDto) {
        fieldServiceImpl.update(id,fieldDto);
        Field field = fieldVmMapper.toField(fieldDto);
        FieldResponseVm fieldResponseVm = fieldVmMapper.toResponseVM(field);
        return new ResponseEntity<>(fieldResponseVm, HttpStatus.OK);
    }
    @GetMapping("all")
    public ResponseEntity<List<FieldResponseVm>> getAll() {
        List<Field> fields = fieldServiceImpl.findAll();
        List<FieldResponseVm> fieldResponseVms = fields.stream()
                .map(fieldVmMapper::toResponseVM)
                .collect(Collectors.toList());
        return new ResponseEntity<>(fieldResponseVms, HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        fieldServiceImpl.delete(id);
        return new ResponseEntity<>("Field deleted successfully", HttpStatus.OK);
    }
}