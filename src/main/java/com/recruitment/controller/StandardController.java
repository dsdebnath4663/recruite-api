package com.recruitment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/standard")
public class StandardController {

    @GetMapping
    public String standardAccess() {
        return "Welcome Standard User. You have intermediate access.";
    }
}
