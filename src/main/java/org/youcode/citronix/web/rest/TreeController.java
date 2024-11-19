package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.service.TreeService;
import org.youcode.citronix.service.impl.TreeServiceImpl;
import org.youcode.citronix.web.vm.farm.FarmResponseVm;
import org.youcode.citronix.web.vm.farm.FarmVm;
import org.youcode.citronix.web.vm.mapper.TreeMapperVm;
import org.youcode.citronix.web.vm.tree.TreeResponseVm;
import org.youcode.citronix.web.vm.tree.TreeVm;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/trees")
public class TreeController {
    private final TreeServiceImpl treeServiceImpl;
    private final TreeMapperVm treeMapperVm;
    public TreeController(TreeServiceImpl treeServiceImpl,  TreeMapperVm treeMapperVm) {
        this.treeServiceImpl = treeServiceImpl;
        this.treeMapperVm = treeMapperVm;
    }
    @PostMapping("save")
    public ResponseEntity<TreeResponseVm> save(@RequestBody @Valid TreeVm treeVm){
        Tree tree = treeMapperVm.toTree(treeVm);
        treeServiceImpl.save(treeVm.getFieldId(), tree);
        TreeResponseVm responseVm = treeMapperVm.toTreeResponseVm(tree);
        return new ResponseEntity<>(responseVm, HttpStatus.CREATED);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<TreeResponseVm> update(@PathVariable UUID id, @RequestBody TreeVm treeVm) {
        Tree tree = treeMapperVm.toTree(treeVm);
        tree.setId(id);
        treeServiceImpl.update(id, tree);
        TreeResponseVm treeResponseVm = treeMapperVm.toTreeResponseVm(tree);
        return new ResponseEntity<>(treeResponseVm, HttpStatus.OK);
    }
    @GetMapping("all")
    public ResponseEntity<List<TreeResponseVm>> getAll() {
        List<Tree> trees = treeServiceImpl.findAll();
        List<TreeResponseVm> treeResponseVmList = trees.stream()
                .map(treeMapperVm::toTreeResponseVm)
                .collect(Collectors.toList());
        return new ResponseEntity<>(treeResponseVmList, HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        treeServiceImpl.delete(id);
        return new ResponseEntity<>("Tree deleted successfully",HttpStatus.OK);
    }
}
