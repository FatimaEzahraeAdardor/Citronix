package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.web.vm.tree.TreeResponseVm;
import org.youcode.citronix.web.vm.tree.TreeVm;

@Mapper(componentModel = "spring", uses = {FieldVmMapper.class})
public interface TreeMapperVm {
    @Mapping(target = "field.id", source = "fieldId")
    Tree toTree(TreeVm treeVm);

    @Mapping(target = "fieldId", source = "field.id")
    TreeVm toTreeVm(Tree tree);

    TreeResponseVm toTreeResponseVm(Tree tree);
}