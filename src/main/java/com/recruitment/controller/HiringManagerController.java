package com.recruitment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hiring-manager")
public class HiringManagerController {

    @GetMapping
    public String hiringManagerAccess() {
        return "Welcome Hiring Manager. You can manage job openings and candidates.";
    }
}
