package com.charter.restApp;

public class Transaction {

    private long id_transaction;
    private long id_customer;
    private int date;
    private int points_earned;

    public Transaction(long id_transaction, long id_customer, int date, int points_earned) {
        this.id_transaction = id_transaction;
        this.id_customer = id_customer;
        this.date = date;
        this.points_earned = points_earned;
    }

    public long getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(long id_transaction) {
        this.id_transaction = id_transaction;
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getPoints_earned() {
        return points_earned;
    }

    public void setPoints_earned(int points_earned) {
        this.points_earned = points_earned;
    }
}