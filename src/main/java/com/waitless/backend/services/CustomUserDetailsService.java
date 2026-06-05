package com.waitless.backend.services;

import com.waitless.backend.security.UserPrincipal;
import com.waitless.backend.model.Users;
import com.waitless.backend.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        System.out.println("LOOKING FOR EMAIL = " + email);

        Optional<Users> users = userRepo.findByEmail(email);

        System.out.println("USER FOUND = " + users.isPresent());

        Users user = users.orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        return new UserPrincipal(user);
    }
}