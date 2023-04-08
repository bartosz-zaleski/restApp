package com.charter.restApp;

import org.mariadb.jdbc.*;
import org.mariadb.jdbc.Connection;

import java.sql.*;
import java.sql.SQLException;
import java.util.logging.*;
import java.io.*;
import java.util.Properties;

public class Database {

    private static Database instance;
    private org.mariadb.jdbc.Connection connection;

    private final String hostname;
    private final String port;
    private final String dbname;
    private final String username;
    private final String password;

    private final Logger logger;

    private Database() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();

        // Create logger

        this.logger = Logger.getLogger(Database.class.getName());

        // Load config

        Properties prop = new Properties();
        FileInputStream config = new FileInputStream("config/db.config");
        prop.load(config);

        if(prop.isEmpty()) {
            throw(new IOException("Config file empty!"));
        }

        this.logger.log(Level.INFO, "Setting up connection to the database:");

        this.hostname = prop.getProperty("hostname");
        this.logger.log(Level.INFO, "hostname: " +this.hostname);

        this.port = prop.getProperty("port");
        this.logger.log(Level.INFO, "port: " +this.port);

        this.dbname = prop.getProperty("dbname");
        this.logger.log(Level.INFO, "database name: " +this.dbname);

        this.username = prop.getProperty("username");
        this.logger.log(Level.INFO, "username: " +this.username);

        this.password = prop.getProperty("password");
        this.logger.log(Level.INFO, "password: " +this.password);

    }

    public static Database getInstance() throws IOException {
        if(Database.instance == null) {
            Database.instance = new Database();
        }
        return Database.instance;
    }

    public void connect() throws SQLException, ClassNotFoundException {

        String connectionUrl = "jdbc:mariadb://" +this.hostname+ ":" +this.port+ "/" +this.dbname;
        this.logger.log(Level.INFO, "Connecting to the database: " +connectionUrl);

        Class.forName("org.mariadb.jdbc.Driver");
        this.connection = (Connection) DriverManager.getConnection(
                connectionUrl,
                this.username,
                this.password
        );
    }

    public void disconnect() {
        try {
            this.connection.close();
        } catch(Exception ex) {
            // Quell, no need to do anything
        }
    }
}
