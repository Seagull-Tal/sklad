package com.example.sklad.dao;

import com.example.sklad.model.Part;
import java.util.List;

public interface PartDAO {
    void create(Part part);
    List<Part> findAll();
    List<Part> findByName(String name);
    Part findById(int id);
    void updateQuantity(int id, int newQty);
}