package com.waitless.backend.controller;

import com.waitless.backend.dto.UserDTO;
import com.waitless.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // FIX: added leading slash — was "admin/{id}" which is fragile with Spring's
    // path normalisation and didn't reliably match the "/users/admin/**" security rule
    @GetMapping("/admin/{id}")
    public ResponseEntity<UserDTO> getUsersById(@PathVariable int id) {
        UserDTO dto = userService.findUserById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(
            @PathVariable int id,
            @Valid @RequestBody UserDTO user) {
        UserDTO dto = userService.updateUserById(id, user);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @GetMapping("/admin/allUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }
}