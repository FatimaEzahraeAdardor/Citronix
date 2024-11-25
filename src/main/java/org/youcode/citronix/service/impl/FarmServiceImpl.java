package org.youcode.citronix.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.repository.FarmRepository;
import org.youcode.citronix.repository.FieldRepository;
import org.youcode.citronix.repository.impl.FarmRepositoryImpl;
import org.youcode.citronix.service.FarmService;
import org.youcode.citronix.service.FieldService;
import org.youcode.citronix.service.dto.FarmSearchDto;
import org.youcode.citronix.web.exception.EntityAlreadyExistsException;
import org.youcode.citronix.web.exception.InvalidObjectException;

import java.util.List;
import java.util.UUID;
@Service
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;
    private final FarmRepositoryImpl farmRepositoryImpl;
    private final FieldRepository fieldRepository;
    public FarmServiceImpl(FarmRepository farmRepository, FarmRepositoryImpl farmRepositoryImpl , FieldRepository fieldRepository) {
        this.farmRepository = farmRepository;
        this.farmRepositoryImpl = farmRepositoryImpl;
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Farm save(Farm farm) {
        if (farm == null){
            throw new InvalidObjectException("Farm object cannot be null.");
        }
        if (farmRepository.existsByName(farm.getName())) {
            throw new EntityAlreadyExistsException("Farm with the name '" + farm.getName() + "' already exists.");
        }
        return farmRepository.save(farm);
    }

    @Override
    public Farm update(Farm farm) {
        Farm updatedfarm = farmRepository.findById(farm.getId()).orElseThrow(()->new InvalidObjectException("Farm not found"));
        updatedfarm.setName(farm.getName());
        updatedfarm.setLocation(farm.getLocation());
        updatedfarm.setCreated_at(farm.getCreated_at());
        updatedfarm.setArea(farm.getArea());
        return farmRepository.save(updatedfarm);

    }

    @Override
    public Farm findById(UUID id) {
        return farmRepository.findById(id).orElseThrow(()->new InvalidObjectException("Farm not found"));

    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Farm farmToDelete = farmRepository.findById(id)
                .orElseThrow(() -> new InvalidObjectException("Farm not found"));
        fieldRepository.deleteByFarmId(farmToDelete.getId());
        farmRepository.delete(farmToDelete);
    }

    @Override
    public List<Farm> findAll() {
        return farmRepository.findAll();
    }

    @Override
    public Page<Farm> findFarmsWithPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return farmRepository.findAll(pageable);
    }

    @Override
    public List<Farm> search(FarmSearchDto searchDto) {
        return farmRepositoryImpl.findByCriteria(searchDto);
    }


}
