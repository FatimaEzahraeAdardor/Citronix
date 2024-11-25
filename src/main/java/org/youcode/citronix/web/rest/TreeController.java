package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.service.FieldService;
import org.youcode.citronix.service.TreeService;
import org.youcode.citronix.service.impl.FieldServiceImpl;
import org.youcode.citronix.service.impl.TreeServiceImpl;
import org.youcode.citronix.web.vm.farm.FarmResponseVm;
import org.youcode.citronix.web.vm.farm.FarmVm;
import org.youcode.citronix.web.vm.field.FieldResponseVm;
import org.youcode.citronix.web.vm.mapper.TreeMapperVm;
import org.youcode.citronix.web.vm.tree.TreeResponseVm;
import org.youcode.citronix.web.vm.tree.TreeVm;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/trees")
public class TreeController {
    private final TreeService treeService;
    private final TreeMapperVm treeMapperVm;
    public TreeController(TreeService treeService,  TreeMapperVm treeMapperVm) {
        this.treeService = treeService;
        this.treeMapperVm = treeMapperVm;
    }
    @PostMapping("save")
    public ResponseEntity<TreeResponseVm> save(@RequestBody @Valid TreeVm treeVm){
        Tree tree = treeMapperVm.toTree(treeVm);
        treeService.save(tree);
        TreeResponseVm responseVm = treeMapperVm.toTreeResponseVm(tree);
        return new ResponseEntity<>(responseVm, HttpStatus.CREATED);
    }
    @PutMapping("update")
    public ResponseEntity<TreeResponseVm> update(@RequestBody @Valid TreeVm treeVm) {
        Tree tree = treeMapperVm.toTree(treeVm);
        Tree treeUpdated =  treeService.update(tree);
        TreeResponseVm treeResponseVm = treeMapperVm.toTreeResponseVm(treeUpdated);
        return new ResponseEntity<>(treeResponseVm, HttpStatus.OK);
    }
    @GetMapping("all")
    public ResponseEntity<List<TreeResponseVm>> getAll() {
        List<Tree> trees = treeService.findAll();
        List<TreeResponseVm> treeResponseVmList = trees.stream()
                .map(treeMapperVm::toTreeResponseVm)
                .collect(Collectors.toList());
        return new ResponseEntity<>(treeResponseVmList, HttpStatus.OK);
    }
    @GetMapping("/details/{treeId}")
    public ResponseEntity<TreeResponseVm> getFieldById(@PathVariable UUID treeId) {
        Tree tree = treeService.findById(treeId);
        return ResponseEntity.ok(treeMapperVm.toTreeResponseVm(tree));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        treeService.delete(id);
        return new ResponseEntity<>("Tree deleted successfully",HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Page<TreeResponseVm>> getTrees(@RequestParam (defaultValue = "0")int page, @RequestParam(defaultValue = "20") int size ) {
        Page<Tree> treePages = treeService.findTreesWithPaginated(page, size);
        Page<TreeResponseVm> treeResponseVmPage = treePages.map(treeMapperVm::toTreeResponseVm);
        return new ResponseEntity<>(treeResponseVmPage, HttpStatus.OK);
    }
}
