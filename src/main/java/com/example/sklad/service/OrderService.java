package com.example.sklad.service;

import com.example.sklad.dao.OrderDAO;
import com.example.sklad.dao.PartDAO;
import com.example.sklad.dao.impl.OrderDAOImpl;
import com.example.sklad.dao.impl.PartDAOImpl;
import com.example.sklad.model.Order;
import com.example.sklad.model.Part;
import com.example.sklad.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final PartDAO partDAO = new PartDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();

    public void createOrder(int partId, int qty) {
        try (Connection conn = DBUtil.getConnection()) {

            conn.setAutoCommit(false); // начало транзакции

            Part part = partDAO.findById(partId);
            if (part == null || part.getQuantity() < qty) {
                System.out.println("Недостаточно запчастей или нет такой запчасти");
                conn.rollback();
                return;
            }

            partDAO.updateQuantity(partId, part.getQuantity() - qty);
            orderDAO.create(new Order(0, partId, qty, "NEW"));

            conn.commit();
            System.out.println("Заказ оформлен!");

        } catch (Exception e) {
            log.error("Ошибка оформления заказа", e);
        }
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderDAO.findByStatus(status);
    }
}