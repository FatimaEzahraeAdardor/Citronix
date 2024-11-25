package org.youcode.citronix.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.HarvestDetails;
import org.youcode.citronix.domain.Tree;

import java.util.List;
import java.util.UUID;


public interface TreeService {
    Tree save(Tree tree);
    Tree findById(UUID id);
    List<Tree> findAll();
    Tree update(Tree tree);
    void delete(UUID id);
    Page<Tree> findTreesWithPaginated(int page, int size);

}
