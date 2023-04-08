package com.charter.restApp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;

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

            output = "DB Connected!";
        } catch(IOException ex) {
            output = "DB Error: Cannot find configuration file";
        }

        return output;
    }
}
