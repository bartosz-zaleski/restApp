package com.charter.restApp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.javapoet.ClassName;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLDecoder;
import java.sql.*;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@RestController
public class RestAppController {

    private static final Logger logger = Logger.getLogger(RestAppController.class.getName());

    @RequestMapping("/")
    public String hello() {
        return "cat README.md";
    }

    @GetMapping("/getPoints")
    @ResponseBody
    public String getPoints(@RequestParam Map<String, String> params) {
        String email = params.get("email");

        if(email == null) {
            RestAppController.logger.log(Level.WARNING, "Incorrect email: null");
            return("ERROR: cannot get points");
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        if(pattern.matcher(email).find() == false) {
            RestAppController.logger.log(Level.WARNING, "Incorrect email: >>" +email+ "<<");
            return("ERROR: incorrect email");
        }

        Customer customer = null;

        try {
            customer = DatabaseController.getInstance().getCustomer(email);
        } catch(SQLException ex) {
            RestAppController.logger.log(Level.WARNING, "DATABASE ERROR");
            RestAppController.logger.log(Level.WARNING, ex.toString());
            return("ERROR: cannot get points");
        } catch(ClassNotFoundException ex) {
            RestAppController.logger.log(Level.WARNING, "CLASSNOTFOUND ERROR");
            RestAppController.logger.log(Level.WARNING, ex.toString());
            return("ERROR: cannot get points");
        } catch(IOException ex) {
            RestAppController.logger.log(Level.WARNING, "IOEXCEPTION ERROR");
            RestAppController.logger.log(Level.WARNING, ex.toString());
            return("ERROR: cannot get points");
        }

        if(customer == null) {
            RestAppController.logger.log(Level.WARNING, "Customer not found >>" + email + "<<");
            return ("ERROR: cannot get points");
        }

        JSONArray points = null;

        try {
            points = DatabaseController.getInstance().getPoints(customer);
        } catch(SQLException ex) {
            RestAppController.logger.log(Level.WARNING, "DATABASE ERROR");
            RestAppController.logger.log(Level.WARNING, ex.toString());
            return("ERROR: cannot get points");
        } catch(ClassNotFoundException ex) {
            RestAppController.logger.log(Level.WARNING, "CLASSNOTFOUND ERROR");
            RestAppController.logger.log(Level.WARNING, ex.toString());
            return("ERROR: cannot get points");
        } catch(IOException ex) {
            RestAppController.logger.log(Level.WARNING, "IOEXCEPTION ERROR");
            RestAppController.logger.log(Level.WARNING, ex.toString());
            return("ERROR: cannot get points");
        }

        return(points.toString());
    }

    @GetMapping("/addTransaction")
    @ResponseBody
    public String addTransaction(@RequestParam Map<String,String> params) {

        Pattern pattern = null;

        String firstname = params.get("firstname");
        if(firstname == null) {
            RestAppController.logger.log(Level.WARNING, "Incorrect firstname: null");
            return("ERROR: cannot add transaction");
        }

        String surname = params.get("surname");
        if(surname == null) {
            RestAppController.logger.log(Level.WARNING, "Incorrect surname: null");
            return("ERROR: cannot add transaction");
        }

        String email = params.get("email");
        if(email == null) {
            RestAppController.logger.log(Level.WARNING, "Incorrect email: null");
            return("ERROR: cannot add transaction");
        }

        String amount = params.get("amount");
        if(amount == null) {
            RestAppController.logger.log(Level.WARNING, "Incorrect amount: null");
            return("ERROR: cannot add transaction");
        }

        // Validate input
        firstname = URLDecoder.decode(firstname);
        surname = URLDecoder.decode(surname);
        email = URLDecoder.decode(params.get("email"));
        amount = URLDecoder.decode(params.get("amount"));

        if(StringUtils.length(firstname) == 0 || StringUtils.isAlphanumeric(firstname) == false) {
            RestAppController.logger.log(Level.WARNING, "Incorrect firstname: >>" +firstname+ "<<");
            return("ERROR: incorrect firstname");
        }

        if(StringUtils.length(surname) == 0 || StringUtils.isAlphanumeric(firstname) == false) {
            RestAppController.logger.log(Level.WARNING, "Incorrect surname: >>" +surname+ "<<");
            return("ERROR: incorrect surname");
        }

        // Regex from OWASP
        pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        if(pattern.matcher(email).find() == false) {
            RestAppController.logger.log(Level.WARNING, "Incorrect email: >>" +email+ "<<");
            return("ERROR: incorrect email");
        }

        // Regex from StackOverflow
        pattern = Pattern.compile("^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$");
        if(pattern.matcher(amount).find() == false) {
            RestAppController.logger.log(Level.WARNING, "Incorrect amount: >>" +amount+ "<<");
            return("ERROR: incorrect transaction amount");
        }

        float cents = Float.parseFloat(amount) * 100;
        int points = PointsCalculator.calculate((int) cents);

        Customer customer = new Customer(firstname, surname, email);
        Transaction transaction = new Transaction(customer, points);

        try {
            DatabaseController.getInstance().addTransaction(customer, transaction);
        } catch(ClassNotFoundException ex) {
            RestAppController.logger.log(Level.WARNING, "CLASS NOT FOUND ERROR");
            RestAppController.logger.log(Level.WARNING, ex.toString());
            return("ERROR: cannot add transaction");
        } catch(SQLException ex) {
            RestAppController.logger.log(Level.WARNING, "DATABASE ERROR");
            RestAppController.logger.log(Level.WARNING, ex.toString());
            RestAppController.logger.log(Level.WARNING, ""+ex.getErrorCode());
            return("ERROR: cannot add transaction");
        } catch(IOException ex) {
            RestAppController.logger.log(Level.WARNING, "DATABASE ERROR");
            RestAppController.logger.log(Level.WARNING, ex.toString());
            return ("ERROR: cannot add transaction");
        }

        return "OK: Transaction added";
    }




}
