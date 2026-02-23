package com.example.sklad.dao.impl;

import com.example.sklad.dao.PartDAO;
import com.example.sklad.model.Part;
import com.example.sklad.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartDAOImpl implements PartDAO {

    private static final Logger log = LoggerFactory.getLogger(PartDAOImpl.class);

    @Override
    public void create(Part part) {
        String sql = "INSERT INTO parts(name, quantity) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, part.getName());
            ps.setInt(2, part.getQuantity());
            ps.executeUpdate();

        } catch (Exception e) {
            log.error("Ошибка добавления запчасти", e);
        }
    }

    @Override
    public List<Part> findAll() {
        List<Part> list = new ArrayList<>();
        String sql = "SELECT * FROM parts";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Part(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity")
                ));
            }

        } catch (Exception e) {
            log.error("Ошибка получения запчастей", e);
        }
        return list;
    }

    @Override
    public List<Part> findByName(String name) {
        List<Part> list = new ArrayList<>();
        String sql = "SELECT * FROM parts WHERE name LIKE ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Part(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity")
                ));
            }

        } catch (Exception e) {
            log.error("Ошибка поиска запчастей по названию", e);
        }
        return list;
    }

    @Override
    public Part findById(int id) {
        String sql = "SELECT * FROM parts WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Part(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity")
                );
            }

        } catch (Exception e) {
            log.error("Ошибка поиска запчасти по id", e);
        }
        return null;
    }

    @Override
    public void updateQuantity(int id, int newQty) {
        String sql = "UPDATE parts SET quantity = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newQty);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            log.error("Ошибка обновления количества запчасти", e);
        }
    }
}