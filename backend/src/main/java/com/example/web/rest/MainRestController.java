package com.example.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

    @GetMapping("/")
    public String helloWorld() {
        System.out.println("request to /");

        return "Hello world!";
    }
}
