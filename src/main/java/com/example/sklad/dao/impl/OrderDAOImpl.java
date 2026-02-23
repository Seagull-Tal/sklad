package com.example.sklad.dao.impl;

import com.example.sklad.dao.OrderDAO;
import com.example.sklad.model.Order;
import com.example.sklad.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private static final Logger log = LoggerFactory.getLogger(OrderDAOImpl.class);

    @Override
    public void create(Order order) {
        String sql = "INSERT INTO orders(part_id, qty, status) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, order.getPartId());
            ps.setInt(2, order.getQty());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();

        } catch (Exception e) {
            log.error("Ошибка создания заказа", e);
        }
    }

    @Override
    public List<Order> findByStatus(String status) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("part_id"),
                        rs.getInt("qty"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            log.error("Ошибка фильтрации заказов по статусу", e);
        }
        return list;
    }
}