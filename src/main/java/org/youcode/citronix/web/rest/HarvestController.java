package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.service.HarvestService;
import org.youcode.citronix.service.impl.FieldServiceImpl;
import org.youcode.citronix.service.impl.TreeServiceImpl;
import org.youcode.citronix.web.vm.harvest.HarvestResponseVm;
import org.youcode.citronix.web.vm.harvest.HarvestVm;
import org.youcode.citronix.web.vm.mapper.HarvestMapperVm;
import org.youcode.citronix.web.vm.mapper.TreeMapperVm;
import org.youcode.citronix.web.vm.tree.TreeResponseVm;
import org.youcode.citronix.web.vm.tree.TreeVm;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/harvests")
public class HarvestController {
    private final HarvestService harvestService;
    private final HarvestMapperVm harvestMapperVm;
    public HarvestController(HarvestService harvestService, HarvestMapperVm harvestMapperVm) {
        this.harvestService = harvestService;
        this.harvestMapperVm = harvestMapperVm;
    }
    @PostMapping("save")
    public ResponseEntity<HarvestResponseVm> save(@RequestBody @Valid HarvestVm harvestVm){
        Harvest harvest = harvestMapperVm.toharvest(harvestVm);
        Harvest harvestSaved= harvestService.save(harvestVm.getFieldId(), harvest);
        HarvestResponseVm harvestResponseVm = harvestMapperVm.toharvestResponseVm(harvestSaved);
        return new ResponseEntity<>(harvestResponseVm, HttpStatus.CREATED);
    }

}
