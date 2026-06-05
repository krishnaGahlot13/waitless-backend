package com.waitless.backend.services;

import com.waitless.backend.config.JwtUtil;
import com.waitless.backend.dto.LoginDTO;
import com.waitless.backend.exception.UnauthorizedException;
import com.waitless.backend.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginService(AuthenticationConfiguration authenticationConfiguration,
                        JwtUtil jwtUtil) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtUtil = jwtUtil;
    }

    public String verify(LoginDTO loginDTO) {

        System.out.println("EMAIL = " + loginDTO.getEmail());

        try {

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );

            System.out.println("LOGIN SUCCESS");

            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            String role = userPrincipal.getUsers().getRole().name();

            return jwtUtil.generateToken(loginDTO.getEmail(), role);

        } catch (Exception e) {

            System.out.println("ERROR CLASS = " + e.getClass().getName());
            System.out.println("ERROR MESSAGE = " + e.getMessage());

            e.printStackTrace();

            throw new UnauthorizedException("Invalid Email or Password");
        }
    }
    }
