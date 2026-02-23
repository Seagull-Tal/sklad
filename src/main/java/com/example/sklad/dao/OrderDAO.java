package com.example.sklad.dao;

import com.example.sklad.model.Order;
import java.util.List;

public interface OrderDAO {
    void create(Order order);
    List<Order> findByStatus(String status);
}