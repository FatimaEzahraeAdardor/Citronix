package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.service.impl.FarmServiceImpl;
import org.youcode.citronix.web.vm.farm.FarmResponseVm;
import org.youcode.citronix.web.vm.farm.FarmVm;
import org.youcode.citronix.web.vm.mapper.FarmVmMapper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/farms")
public class FarmController {
    private final FarmServiceImpl farmServiceImpl;
    private final FarmVmMapper farmVmMapper;
    public FarmController(FarmServiceImpl farmServiceImpl, FarmVmMapper farmVmMapper) {
        this.farmServiceImpl = farmServiceImpl;
        this.farmVmMapper = farmVmMapper;
    }
    @PostMapping("/save")
    public ResponseEntity<FarmResponseVm> save(@RequestBody @Valid FarmVm farmVm){
        Farm farm = farmVmMapper.toFarm(farmVm);
        farmServiceImpl.save(farm);
        FarmResponseVm farmResponseVm = farmVmMapper.toResponseVM(farm);
        return new ResponseEntity<>(farmResponseVm , HttpStatus.CREATED);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<FarmResponseVm> update(@PathVariable UUID id, @RequestBody @Valid FarmVm farmVm){
        Farm farm = farmVmMapper.toFarm(farmVm);
        farm.setId(id);
        farmServiceImpl.update(farm);
        FarmResponseVm farmResponseVm = farmVmMapper.toResponseVM(farm);
        return new ResponseEntity<>(farmResponseVm , HttpStatus.OK);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        farmServiceImpl.delete(id);
        return new ResponseEntity<>("Farm deleted successfully", HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<FarmResponseVm>> getAll() {
        List<Farm> farmList = farmServiceImpl.findAll();
        List<FarmResponseVm> farmResponseVm = farmList.stream()
                .map(farmVmMapper::toResponseVM)
                .collect(Collectors.toList());
        return new ResponseEntity<>(farmResponseVm, HttpStatus.OK);
    }

    @GetMapping("page")
    public ResponseEntity<Page<FarmResponseVm>> getPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "20") int size){
        Page<Farm> farmPage = farmServiceImpl.findFarmsWithPaginated(page, size);
        Page<FarmResponseVm> farmResponseVm = farmPage.map(farmVmMapper::toResponseVM);
        return new ResponseEntity<>(farmResponseVm, HttpStatus.OK);
    }

}
