package com.waitless.backend.controller;


import com.waitless.backend.dto.BusinessRegisterDTO;
import com.waitless.backend.dto.BusinessRegisterResponseDTO;
import com.waitless.backend.services.BusinessRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class BusinessRegistrationController {

    @Autowired
    private BusinessRegisterService businessRegisterService;

    @PostMapping("/business/registration")
    public BusinessRegisterResponseDTO businessRegister (@Valid @RequestBody BusinessRegisterDTO dto){
        return businessRegisterService.businessRegister(dto);
    }
}
