package com.charter.restApp;

public class Transaction {

    private long id_transaction;
    private Customer customer;
    private int points_earned;

    public Transaction(Customer customer, int points_earned) {
        this.customer = customer;
        this.points_earned = points_earned;
    }

    public int getPoints_earned() {
        return points_earned;
    }
}