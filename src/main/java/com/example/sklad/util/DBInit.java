package com.example.sklad.util;

import java.sql.Connection;
import java.sql.Statement;

public class DBInit {

    public static void init() {
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement()) {

            st.execute("""
                CREATE TABLE IF NOT EXISTS parts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    quantity INTEGER NOT NULL
                );
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    part_id INTEGER NOT NULL,
                    qty INTEGER NOT NULL,
                    status TEXT NOT NULL,
                    FOREIGN KEY (part_id) REFERENCES parts(id)
                );
            """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}