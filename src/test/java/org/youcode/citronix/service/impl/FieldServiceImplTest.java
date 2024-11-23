package org.youcode.citronix.service.impl;

import org.awaitility.reflect.exception.FieldNotFoundException;
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
    @Test
    void FieldService_addField_throwsInvalidObjectException_whenFarmHasMoreThan10Fields(){
        Field field = new Field();
        field.setArea(30);
        field.setFarm(farm);
        for (int i = 0; i < 11; i++) {
            farm.getFields().add(new Field());
        }
        when(farmService.findById(farm.getId())).thenReturn(farm);
        assertThrows(InvalidObjectException.class, () ->
                fieldServiceImpl.save(field));
        verify(fieldRepository,never()).save(field);


    }

    @Test
    void FieldService_findById_succeed(){
        UUID id = UUID.randomUUID();
        Field field = new Field();
        field.setId(id);
        field.setArea(20);
        field.setFarm(farm);
        when(fieldRepository.findById(id)).thenReturn(Optional.of(field));
        Field savedField = fieldServiceImpl.findById(id);
        assertNotNull(savedField);
        assertEquals(id, savedField.getId());
        verify(fieldRepository).findById(id);

    }
    @Test
    void FieldService_findById_throwsInvalidObjectException(){
        UUID id = UUID.randomUUID();
        when(fieldRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(InvalidObjectException.class, () -> fieldServiceImpl.findById(id));
        verify(fieldRepository).findById(id);
    }

    @Test
    void FieldService_updateField_succeed(){
        UUID id = UUID.randomUUID();
        Field field = new Field();
        field.setId(id);
        field.setArea(10);
        field.setFarm(farm);
        Field updatedField = new Field();
        updatedField.setArea(20);
        updatedField.setFarm(farm);
        when(fieldRepository.findById(id)).thenReturn(Optional.of(field));
        when(farmService.findById(farm.getId())).thenReturn(farm);
        when(fieldRepository.save(field)).thenReturn(updatedField);
        Field savedField = fieldServiceImpl.update(id, updatedField);
        assertNotNull(savedField);
        assertEquals(20, savedField.getArea());
        verify(fieldRepository).save(field);
    }

    }
