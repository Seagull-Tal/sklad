package com.example.sklad.model;

public class Order {
    private int id;
    private int partId;
    private int qty;
    private String status;

    public Order() {}

    public Order(int id, int partId, int qty, String status) {
        this.id = id;
        this.partId = partId;
        this.qty = qty;
        this.status = status;
    }

    public int getId() { return id; }
    public int getPartId() { return partId; }
    public int getQty() { return qty; }
    public String getStatus() { return status; }
}