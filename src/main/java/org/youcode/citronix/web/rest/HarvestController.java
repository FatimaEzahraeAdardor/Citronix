package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.domain.enums.Saison;
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

    @GetMapping("all")
    public ResponseEntity<List<HarvestResponseVm>> getAll() {
        List<Harvest> harvests = harvestService.findAll();
        List<HarvestResponseVm> harvestResponseVmList = harvests.stream()
                .map(harvest -> harvestMapperVm.toharvestResponseVm(harvest))
                .collect(Collectors.toList());
        return new ResponseEntity<>(harvestResponseVmList, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<HarvestResponseVm>> getSeason(@RequestParam Saison season) {
        List<Harvest> harvests = harvestService.findHarvestsBySeason(season);
        List<HarvestResponseVm> harvestResponseVmList = harvests.stream()
                .map(harvest -> harvestMapperVm.toharvestResponseVm(harvest))
                .collect(Collectors.toList());
        return new ResponseEntity<>(harvestResponseVmList, HttpStatus.OK);

    }
    @PutMapping("update")
    public ResponseEntity<HarvestResponseVm> update(@RequestBody @Valid HarvestVm harvestVm) {
        Harvest harvest = harvestMapperVm.toharvest(harvestVm);
        Harvest harvestSaved= harvestService.update(harvestVm.getFieldId(), harvest);
        HarvestResponseVm harvestResponseVm = harvestMapperVm.toharvestResponseVm(harvestSaved);
        return new ResponseEntity<>(harvestResponseVm, HttpStatus.OK);
    }
    @GetMapping("page")
    public ResponseEntity<Page<HarvestResponseVm>> getPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        Page<Harvest> harvestPage = harvestService.findHarvestPaginated(page, size);
        Page<HarvestResponseVm> harvestResponseVmPage = harvestPage.map(harvestMapperVm::toharvestResponseVm);
        return new ResponseEntity<>(harvestResponseVmPage, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        harvestService.delete(id);
        return new ResponseEntity<>("Harvest deleted successfully", HttpStatus.OK);

    }


}
