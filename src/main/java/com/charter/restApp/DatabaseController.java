package com.charter.restApp;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class DatabaseController {

    private static DatabaseController instance;

    private Database db;
    private final Logger logger;

    private DatabaseController() throws IOException, SQLException, ClassNotFoundException {

        this.logger = Logger.getLogger(DatabaseController.class.getName());

        this.db = Database.getInstance();
        db.connect();
    }

    public static DatabaseController getInstance() throws IOException, SQLException, ClassNotFoundException {
        if (DatabaseController.instance == null) {
            DatabaseController.instance = new DatabaseController();
        }

        return DatabaseController.instance;
    }

    public Customer getCustomer(String email) throws SQLException {

        // Check whether the customer exists
        PreparedStatement customer = DatabaseController.instance.db.getConnection().prepareStatement("SELECT id_customer, firstname, surname, email FROM customers WHERE email=? LIMIT 1");
        customer.setString(1, email);

        ResultSet res = customer.executeQuery();
        res.last();
        if(res.getRow() == 0) {
            this.logger.log(Level.INFO, "Customer not found: >>" +email+ "<<");
            return(null);
        } else {
            Customer ret = new Customer(res.getString("firstname"), res.getString("surname"), res.getString("email"));
            return(ret);
        }
    }

    public Customer addCustomer(String firstname, String surname, String email) throws SQLException {
// Check whether the customer exists
        PreparedStatement customer = DatabaseController.instance.db.getConnection().prepareStatement("INSERT INTO customers(firstname, surname, email) VALUES (?, ?, ?)");
        customer.setString(1, firstname);
        customer.setString(2, surname);
        customer.setString(3, email);

        ResultSet res = customer.executeQuery();
        res.last();
        if(res.getRow() == 0) {
            this.logger.log(Level.WARNING, "Error adding customer: >>" +firstname+ ", " +surname+ ", " +email+ "<<");
            return(null);
        } else {
            this.logger.log(Level.INFO, "Added customer: >>" +firstname+ ", " +surname+ ", " +email+ "<<");
            Customer ret = new Customer(res.getString("firstname"), res.getString("surname"), res.getString("email"));
            return(ret);
        }
    }

    public JSONObject getPoints(Customer customer) throws SQLException {
        PreparedStatement getPoints = DatabaseController.instance.db.getConnection().prepareStatement(
                "SELECT firstname, surname, email, SUM(points_earned) FROM customers \n" +
                "INNER JOIN (SELECT id_customer, points_earned FROM transactions WHERE date>CURDATE() - INTERVAL 30 DAY) AS transactions \n" +
                "ON customers.id_customer=transactions.id_customer\n" +
                "GROUP BY customers.id_customer ");
        ResultSet res = getPoints.executeQuery();

    }

    public void addTransaction(Customer customer, Transaction transaction) throws SQLException {
        PreparedStatement addTransaction = DatabaseController.instance.db.getConnection().prepareStatement("CALL add_transaction(?, ?, ?, ?);");
        addTransaction.setString(1, customer.getFirstname());
        addTransaction.setString(2, customer.getSurname());
        addTransaction.setString(3, customer.getEmail());
        addTransaction.setInt(4, transaction.getPoints_earned());

        ResultSet res = addTransaction.executeQuery();
        res.last();

        if(res.getRow() == 1) {
            JSONObject transactionAdded = new JSONObject();
            transactionAdded.put("firstname", customer.getFirstname());
            transactionAdded.put("surname", customer.getSurname());
            transactionAdded.put("email", customer.getEmail());
            transactionAdded.put("points", transaction.getPoints_earned());

            this.logger.log(Level.INFO, "Transaction added: " +transactionAdded);
        } else {
            this.logger.log(Level.WARNING, "Transaction not added");
        }
    }
}