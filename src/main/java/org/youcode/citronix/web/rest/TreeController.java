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
}
