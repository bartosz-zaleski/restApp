package com.charter.restApp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.sql.*;

@RestController
public class RestAppController {

    @RequestMapping("/")
    public String hello() {
        return "Hello javaTpoint";
    }

    @RequestMapping("/hello")
    public String oi() {

        String output = "";

        try {
            Database db = Database.getInstance();

            db.connect();
            output = "DB Connected!";

        } catch(IOException ex) {
            output = "DB Error: Cannot find configuration file";
        } catch(SQLException ex) {
            output = "DB Error: Cannot connect to the database";
        } catch(ClassNotFoundException ex) {
            output = "DB Error: Cannot find class org.mariadb.jdbc.Driver";
        }

        return output;
    }
}
