package org.youcode.citronix.service.impl;

import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.repository.FarmRepository;
import org.youcode.citronix.service.FarmService;
import org.youcode.citronix.web.exception.EntityAlreadyExistsException;
import org.youcode.citronix.web.exception.InvalidObjectException;

import java.util.UUID;
@Service
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;
    public FarmServiceImpl(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
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
    public void delete(UUID id) {
        Farm deletedfarm = farmRepository.findById(id).orElseThrow(()->new InvalidObjectException("Farm not found"));
        farmRepository.delete(deletedfarm);
    }
}
