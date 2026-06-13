package com.waitless.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class healthController {
    @GetMapping("/health")
    public String health(){
        return "ok";
    }
}
