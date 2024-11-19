package org.youcode.citronix.service.impl;

import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.repository.TreeRepository;
import org.youcode.citronix.service.TreeService;
import org.youcode.citronix.web.exception.InvalidObjectException;

import java.util.List;
import java.util.UUID;
@Service
public class TreeServiceImpl implements TreeService {
    private final TreeRepository treeRepository;
    private final FieldServiceImpl fieldServiceImpl;
    public TreeServiceImpl(TreeRepository treeRepository, FieldServiceImpl fieldServiceImpl) {
        this.treeRepository = treeRepository;
        this.fieldServiceImpl = fieldServiceImpl;
    }
    @Override
    public Tree save(UUID fieldId, Tree tree) {
        if (tree == null) {
            throw new InvalidObjectException("Field object cannot be null.");
        }
        Field field = fieldServiceImpl.findById(fieldId);
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
    public Tree update(Tree tree) {
        return null;
    }

    @Override
    public void delete(Tree tree) {

    }
    public boolean isPlantingSeason(Tree tree) {
        if(tree.getPlanting_date()==null){
            return false;
        }
        if(tree.getPlanting_date().getMonthValue() >=3 && tree.getPlanting_date().getMonthValue() <= 5){
            return true;
        }
        return false;
    }
}
