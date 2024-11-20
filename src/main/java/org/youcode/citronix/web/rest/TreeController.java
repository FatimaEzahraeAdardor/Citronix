package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.domain.Tree;
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
    private final TreeServiceImpl treeServiceImpl;
    private final FieldServiceImpl fieldServiceImpl;
    private final TreeMapperVm treeMapperVm;
    public TreeController(TreeServiceImpl treeServiceImpl,  TreeMapperVm treeMapperVm, FieldServiceImpl fieldServiceImpl) {
        this.treeServiceImpl = treeServiceImpl;
        this.treeMapperVm = treeMapperVm;
        this.fieldServiceImpl = fieldServiceImpl;
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
        Field field = fieldServiceImpl.findById(treeVm.getFieldId());
        tree.setField(field);
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
    @GetMapping
    public ResponseEntity<Page<TreeResponseVm>> getTrees(@RequestParam (defaultValue = "0")int page, @RequestParam(defaultValue = "20") int size ) {
        Page<Tree> treePages = treeServiceImpl.findTreesWithPaginated(page, size);
        Page<TreeResponseVm> treeResponseVmPage = treePages.map(treeMapperVm::toTreeResponseVm);
        return new ResponseEntity<>(treeResponseVmPage, HttpStatus.OK);
    }
}
