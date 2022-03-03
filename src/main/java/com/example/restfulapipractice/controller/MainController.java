package com.example.restfulapipractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String index(){

        return "<h1>localhost:8080:isRunning...</h1>";
    }
}
