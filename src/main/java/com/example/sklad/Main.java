package com.example.sklad;

import com.example.sklad.dao.PartDAO;
import com.example.sklad.dao.impl.PartDAOImpl;
import com.example.sklad.model.Part;
import com.example.sklad.service.OrderService;
import com.example.sklad.util.DBInit;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DBInit.init();

        PartDAO partDAO = new PartDAOImpl();
        OrderService orderService = new OrderService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    1. Добавить запчасть
                    2. Показать все запчасти
                    3. Поиск запчастей по названию
                    4. Оформить заказ
                    5. Показать заказы по статусу
                    0. Выход
                    """);

            int cmd = sc.nextInt();
            sc.nextLine();

            if (cmd == 1) {
                System.out.print("Название: ");
                String name = sc.nextLine();
                System.out.print("Количество: ");
                int qty = sc.nextInt();
                partDAO.create(new Part(0, name, qty));
            }

            if (cmd == 2) {
                partDAO.findAll().forEach(p ->
                        System.out.println(p.getId() + " | " + p.getName() + " | " + p.getQuantity()));
            }

            if (cmd == 3) {
                System.out.print("Введите часть названия: ");
                String s = sc.nextLine();
                partDAO.findByName(s).forEach(p ->
                        System.out.println(p.getId() + " | " + p.getName() + " | " + p.getQuantity()));
            }

            if (cmd == 4) {
                System.out.print("ID запчасти: ");
                int id = sc.nextInt();
                System.out.print("Количество: ");
                int qty = sc.nextInt();
                orderService.createOrder(id, qty);
            }

            if (cmd == 5) {
                System.out.print("Статус (например NEW): ");
                String status = sc.nextLine();
                orderService.getOrdersByStatus(status)
                        .forEach(o -> System.out.println(
                                o.getId() + " | " + o.getPartId() + " | " + o.getQty() + " | " + o.getStatus()
                        ));
            }

            if (cmd == 0) break;
        }
    }
}