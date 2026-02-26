package com.example.sklad.dao;

import com.example.sklad.dao.impl.PartDAOImpl;
import com.example.sklad.model.Part;
import com.example.sklad.util.DBInit;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartDAOTest {

    private PartDAO partDAO;

    @BeforeEach
    void setUp() {
        DBInit.init();
        partDAO = new PartDAOImpl();
    }

    @Test
    void findByName_existingName_shouldReturnResult() {
        Part part = new Part(1, "Wheel", 3);

        partDAO.create(part);

        List<Part> result = partDAO.findByName("Wheel");
        assertEquals(1, result.size());
    }

    @Test
    void findByName_notExistingName_shouldReturnEmptyList() {
        List<Part> result = partDAO.findByName("qwerty123");
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnListAfterSave() {
        partDAO.create(new Part(2, "Bolt", 10));
        assertFalse(partDAO.findAll().isEmpty());
    }

    @Test
    void save_shouldPersistPart() {
        partDAO.create(new Part(3, "Nut", 5));
        List<Part> parts = partDAO.findAll();
        assertTrue(parts.stream().anyMatch(p -> p.getName().equals("Nut")));
    }
}
