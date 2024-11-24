package org.youcode.citronix.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.repository.TreeRepository;
import org.youcode.citronix.service.FieldService;
import org.youcode.citronix.service.TreeService;
import org.youcode.citronix.web.exception.InvalidObjectException;

import java.util.List;
import java.util.UUID;
@Service
public class TreeServiceImpl implements TreeService {
    private final TreeRepository treeRepository;
    private final FieldService fieldService;
    public TreeServiceImpl(TreeRepository treeRepository, FieldService fieldService) {
        this.treeRepository = treeRepository;
        this.fieldService = fieldService;
    }
    @Override
    public Tree save(Tree tree) {
        if (tree == null) {
            throw new InvalidObjectException("tree object cannot be null.");
        }
        Field field = fieldService.findById(tree.getField().getId());
        if (field.getTrees().size() >= field.getArea() * 100) {
            throw new InvalidObjectException("Cannot add tree. Maximum tree density of 100 trees per hectare exceeded.");
        }
        if (!isPlantingSeason(tree)) {
            throw new InvalidObjectException("Cannot add tree. Planting season is not valid.");

        }
        tree.setField(field);
       return treeRepository.save(tree);

      }

    @Override
    public Tree findById(UUID id) {
        return treeRepository.findById(id).orElseThrow(()->new InvalidObjectException("Cannot find tree with id " + id));
    }

    @Override
    public List<Tree> findAll() {
        return treeRepository.findAll();
    }

    @Override
    public Tree update(Tree updatedTree) {
        Tree existingTree = findById(updatedTree.getId());
        Field field = fieldService.findById(updatedTree.getField().getId());

        if (field.getTrees().size() >= field.getArea() * 100) {
            throw new InvalidObjectException("Cannot update tree. Maximum density of 100 trees per hectare exceeded.");
        }
        if (!isPlantingSeason(updatedTree)) {
            throw new InvalidObjectException("Cannot update tree. Planting season is not valid.");
        }
        existingTree.setPlanting_date(updatedTree.getPlanting_date());
        existingTree.setField(field);
        return treeRepository.save(existingTree);
    }


    @Override
    public void delete(UUID id) {
        Tree tree = findById(id);
        treeRepository.delete(tree);

    }

    @Override
    public Page<Tree> findTreesWithPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return treeRepository.findAll(pageable);
    }

    private boolean isPlantingSeason(Tree tree) {
        if(tree.getPlanting_date()==null){
            return false;
        }
        if(tree.getPlanting_date().getMonthValue() >=3 && tree.getPlanting_date().getMonthValue() <= 5){
            return true;
        }
        return false;
    }
}
