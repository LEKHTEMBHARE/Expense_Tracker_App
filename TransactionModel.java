package com.example.expensetracker;

public class TransactionModel {

    private int id;
    private String type;      // income / expense
    private int amount;
    private String category;
    private String date;      // yyyy-MM-dd

    // Constructor
    public TransactionModel(int id, String type, int amount, String category, String date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    // Optional: Setters (future edit feature ke liye)
    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }
}