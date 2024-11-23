package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.service.FieldService;
import org.youcode.citronix.service.impl.FieldServiceImpl;
import org.youcode.citronix.web.vm.field.FieldResponseVm;
import org.youcode.citronix.web.vm.field.FieldVm;
import org.youcode.citronix.web.vm.mapper.FieldVmMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/fields")
public class FieldController {
    private final FieldService fieldService;
    private final FieldVmMapper fieldVmMapper;
    public FieldController(FieldService fieldService, FieldVmMapper fieldVmMapper) {
        this.fieldService = fieldService;
        this.fieldVmMapper = fieldVmMapper;
    }

    @PostMapping("save")
    public ResponseEntity<FieldResponseVm> save( @Valid @RequestBody FieldVm fieldVm) {
        Field fieldToSave = fieldVmMapper.toField(fieldVm);
        Field createdField = fieldService.save(fieldToSave);
        FieldResponseVm fieldResponseVm = fieldVmMapper.toResponseVM(createdField);
        return new ResponseEntity<>(fieldResponseVm, HttpStatus.CREATED);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<FieldResponseVm> update(@PathVariable UUID id, @RequestBody @Valid FieldVm fieldVm) {
        Field fieldToUpdate = fieldVmMapper.toField(fieldVm);
        Field field = fieldService.update(id,fieldToUpdate);
        FieldResponseVm fieldResponseVm = fieldVmMapper.toResponseVM(field);
        return new ResponseEntity<>(fieldResponseVm, HttpStatus.OK);
    }
    @GetMapping("all")
    public ResponseEntity<List<FieldResponseVm>> getAll() {
        List<Field> fields = fieldService.findAll();
        List<FieldResponseVm> fieldResponseVms = fields.stream()
                .map(fieldVmMapper::toResponseVM)
                .collect(Collectors.toList());
        return new ResponseEntity<>(fieldResponseVms, HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        fieldService.delete(id);
        return new ResponseEntity<>("Field deleted successfully", HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Page<FieldResponseVm>> getFields(@RequestParam (defaultValue = "0")int page, @RequestParam(defaultValue = "20") int size ) {
        Page<Field> fieldsPages = fieldService.findFieldsWithPaginated(page, size);
        Page<FieldResponseVm> fieldResponseVms = fieldsPages.map(fieldVmMapper::toResponseVM);
        return new ResponseEntity<>(fieldResponseVms, HttpStatus.OK);
    }
}