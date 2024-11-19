package org.youcode.citronix.service;

import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Tree;

import java.util.List;
import java.util.UUID;


public interface TreeService {
    Tree save(UUID fieldId, Tree tree);
    Tree findById(UUID id);
    List<Tree> findAll();
    Tree update(UUID id ,Tree tree);
    void delete(UUID id);

}
