package org.youcode.citronix.service;

import org.youcode.citronix.domain.Farm;

import java.util.UUID;

public interface FarmService {
    Farm save(Farm farm);
    Farm update(Farm farm);
    Farm findById(UUID id);
    void delete(UUID id);
}
