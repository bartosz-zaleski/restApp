package com.charter.restApp;

public class Customer {

    private long id_customer;
    private String firstname;
    private String surname;
    private String email;

    public Customer(String firstname, String surname, String email) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
