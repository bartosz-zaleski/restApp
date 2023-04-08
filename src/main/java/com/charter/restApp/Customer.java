package com.charter.restApp;

public class Customer {

    private long id_customer;
    private String firstname;
    private String surname;
    private String email;
    private int date_joined;

    public Customer(long id_customer, String firstname, String surname, String email, int date_joined) {
        this.id_customer = id_customer;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.date_joined = date_joined;
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(int date_joined) {
        this.date_joined = date_joined;
    }
}
