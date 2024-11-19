package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.web.vm.tree.TreeResponseVm;
import org.youcode.citronix.web.vm.tree.TreeVm;

@Mapper(componentModel = "spring")
public interface TreeMapperVm {
    Tree toTree(TreeVm treeVm);
    TreeVm toTreeVm(Tree tree);
    TreeResponseVm toTreeResponseVm(Tree tree);

}
