package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.service.impl.FarmServiceImpl;
import org.youcode.citronix.web.vm.farm.FarmResponseVm;
import org.youcode.citronix.web.vm.farm.FarmVm;
import org.youcode.citronix.web.vm.mapper.FarmVmMapper;

import java.util.UUID;

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
}
