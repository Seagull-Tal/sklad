package com.example.sklad;

import com.example.sklad.dao.OrderDAO;
import com.example.sklad.dao.PartDAO;
import com.example.sklad.dao.impl.OrderDAOImpl;
import com.example.sklad.dao.impl.PartDAOImpl;
import com.example.sklad.model.Order;
import com.example.sklad.model.Part;
import com.example.sklad.util.DBInit;
import com.example.sklad.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalTest {

    @BeforeEach
    void cleanDb() throws Exception {
        DBInit.init();
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM orders");
            st.executeUpdate("DELETE FROM parts");
        }
    }

    @Test
    void fullFlow_createAndFindPart() {
        PartDAO partDAO = new PartDAOImpl();

        partDAO.create(new Part(1, "Wheel", 5));

        List<Part> parts = partDAO.findByName("Wheel");

        assertFalse(parts.isEmpty());
        Part saved = parts.get(0);

        assertEquals("Wheel", saved.getName());
        assertEquals(5, saved.getQuantity());
    }

    @Test
    void fullFlow_createOrderAndFindByStatus() {
        PartDAO partDAO = new PartDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();

        partDAO.create(new Part(2, "Door", 3));
        orderDAO.create(new Order(0, 2, 1, "NEW"));

        List<Order> orders = orderDAO.findByStatus("NEW");

        assertFalse(orders.isEmpty());
        assertEquals("NEW", orders.get(0).getStatus());
    }
}
