package org.youcode.citronix.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.repository.FieldRepository;
import org.youcode.citronix.service.FarmService;
import org.youcode.citronix.web.exception.InvalidObjectException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmService farmService;

    @InjectMocks
    private FieldServiceImpl fieldServiceImpl;

    private Farm farm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        farm = new Farm();
        farm.setId(UUID.randomUUID());
        farm.setName("Farm");
        farm.setLocation("Location");
        farm.setArea(100.0);
        farm.setFields(new ArrayList<>());
    }

    @Test
    void FieldService_saveField_succeed() {
        Field field = new Field();
        field.setArea(10.0);
        field.setFarm(farm);

        Field savedFieldMock = new Field();
        savedFieldMock.setId(UUID.randomUUID());
        savedFieldMock.setArea(10.0);
        savedFieldMock.setFarm(farm);

        when(farmService.findById(farm.getId())).thenReturn(farm);
        when(fieldRepository.save(field)).thenReturn(savedFieldMock);

        Field savedField = fieldServiceImpl.save(field);


        assertNotNull(savedField);
        assertEquals(savedFieldMock.getId(), savedField.getId());
        verify(fieldRepository).save(field);
    }
    @Test
    void  FieldService_saveField_throwsInvalidObjectException_whenFieldAreaTooSmall(){
        Field field = new Field();
        field.setArea(0.05);
        field.setFarm(farm);
        when(farmService.findById(farm.getId())).thenReturn(farm);
        assertThrows(InvalidObjectException.class, () ->
                fieldServiceImpl.save(field));
        verify(fieldRepository,never()).save(field);


    }
    @Test
    void FieldService_saveField_throwsInvalidObjectException_whenFieldAreaExceedsFarmArea() {
        Field field = new Field();
        field.setArea(80);
        field.setFarm(farm);
        when(farmService.findById(farm.getId())).thenReturn(farm);
        assertThrows(InvalidObjectException.class, () ->
                fieldServiceImpl.save(field));
        verify(fieldRepository,never()).save(field);
    }



    }
