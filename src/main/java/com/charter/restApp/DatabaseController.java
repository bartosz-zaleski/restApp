package com.charter.restApp;

import java.io.IOException;
import java.sql.PreparedStatement;
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

    public void addTransaction(Customer customer, Transaction transaction) throws SQLException {
        PreparedStatement addTransaction = DatabaseController.instance.db.getConnection().prepareStatement("CALL add_transaction(?, ?, ?, ?);");
        addTransaction.setString(1, customer.getFirstname());
        addTransaction.setString(2, customer.getSurname());
        addTransaction.setString(3, customer.getEmail());
        addTransaction.setInt(4, transaction.getPoints_earned());

        int res = addTransaction.executeUpdate();

        if(res == 1) {
            JSONObject transactionAdded = new JSONObject();
            transactionAdded.put("firstname", customer.getFirstname());
            transactionAdded.put("firstname", customer.getSurname());
            transactionAdded.put("firstname", customer.getEmail());
            transactionAdded.put("firstname", transaction.getPoints_earned());

            this.logger.log(Level.INFO, "Transaction added: " +transactionAdded);
        } else {
            this.logger.log(Level.WARNING, "Transaction not added");
        }
    }
}