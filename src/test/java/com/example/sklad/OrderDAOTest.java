package com.example.sklad.dao;

import com.example.sklad.dao.impl.OrderDAOImpl;
import com.example.sklad.model.Order;
import com.example.sklad.util.DBInit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDAOTest {

    private OrderDAO orderDAO;

    @BeforeEach
    void setUp() {
        DBInit.init();
        orderDAO = new OrderDAOImpl();
    }

    @Test
    void create_shouldPersistOrder() {
        Order order = new Order(0, 1, 2, "NEW");
        orderDAO.create(order);

        List<Order> orders = orderDAO.findByStatus("NEW");
        assertFalse(orders.isEmpty());
    }

    @Test
    void findByStatus_shouldReturnOnlyWithGivenStatus() {
        orderDAO.create(new Order(0, 1, 1, "NEW"));
        orderDAO.create(new Order(0, 1, 1, "DONE"));

        List<Order> newOrders = orderDAO.findByStatus("NEW");
        List<Order> doneOrders = orderDAO.findByStatus("DONE");

        assertTrue(newOrders.stream().allMatch(o -> o.getStatus().equals("NEW")));
        assertTrue(doneOrders.stream().allMatch(o -> o.getStatus().equals("DONE")));
    }
}
