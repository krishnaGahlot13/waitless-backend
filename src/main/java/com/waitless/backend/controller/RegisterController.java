package com.waitless.backend.controller;

import com.waitless.backend.dto.RegisterDTO;
import com.waitless.backend.dto.RegisterResponseDTO;
import com.waitless.backend.services.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterDTO dto) {
        return ResponseEntity.ok(registerService.registerService(dto));
    }
}