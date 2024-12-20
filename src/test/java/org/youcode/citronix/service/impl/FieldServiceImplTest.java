package org.youcode.citronix.service.impl;

import org.awaitility.reflect.exception.FieldNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.repository.FieldRepository;
import org.youcode.citronix.service.FarmService;
import org.youcode.citronix.web.exception.InvalidObjectException;

import java.util.*;

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
    void FieldService_findAll_returnsListOfFields() {
        Field field1 = new Field();
        field1.setId(UUID.randomUUID());
        field1.setArea(10.0);
        field1.setFarm(farm);

        Field field2 = new Field();
        field2.setId(UUID.randomUUID());
        field2.setArea(20.0);
        field2.setFarm(farm);
        List<Field> fields = Arrays.asList(field1, field2);
        when(fieldRepository.findAll()).thenReturn(fields);
        List<Field> allFields = fieldServiceImpl.findAll();
        assertNotNull(allFields);
        assertEquals(2, allFields.size());
        assertEquals(field1, allFields.get(0));
        assertEquals(field2, allFields.get(1));
    }
    @Test
    void FieldService_findAll_returnsEmptyList() {
        when(fieldRepository.findAll()).thenReturn(Collections.emptyList());
        List<Field> result = fieldServiceImpl.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
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
        when(fieldRepository.save(field)).thenReturn(field);
        Field savedField = fieldServiceImpl.update(id, updatedField);
        assertNotNull(savedField);
        assertEquals(20, savedField.getArea());
        verify(fieldRepository).save(field);
    }
    @Test
    void FieldService_deleteField_succeed(){
        UUID id = UUID.randomUUID();
        Field field = new Field();
        field.setId(id);
        field.setArea(20);
        field.setFarm(farm);
        when(fieldRepository.findById(id)).thenReturn(Optional.of(field));
        fieldServiceImpl.delete(id);
    }
    @Test
    void FieldService_findFieldsWithPaginated_success(){
        int page = 0;
        int size = 5;
        PageRequest pageable = PageRequest.of(page, size);
        List<Field> fields = List.of(new Field(), new Field());
        Page<Field> fieldPage = new PageImpl<>(fields, pageable, fields.size());
        when(fieldRepository.findAll(pageable)).thenReturn(fieldPage);
        Page<Field> result = fieldServiceImpl.findFieldsWithPaginated(page, size);
        assertEquals(2, result.getContent().size());
        verify(fieldRepository).findAll(pageable);
    }

    }
