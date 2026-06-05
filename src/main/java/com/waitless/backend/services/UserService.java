package com.waitless.backend.services;

import com.waitless.backend.dto.UserDTO;
import com.waitless.backend.dto.UserProfileDTO;
import com.waitless.backend.exception.ResourceNotFoundException;
import com.waitless.backend.model.Users;
import com.waitless.backend.repository.UserRepo;
import com.waitless.backend.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    private UserDTO mapToDTO(Users user){

        UserDTO dto = new UserDTO();

        dto.setMobileNumber(user.getMobileNumber());
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public UserDTO findUserById(int id){
        UserDTO dto = new UserDTO();

        Users users = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToDTO(users);
    }


    @Transactional
    public UserDTO updateUserById(int id, @Valid UserDTO updatedUser ){

        int currentUserId = SecurityUtils.getCurrentUserId();

        if (currentUserId != id &&
                !SecurityUtils.isAdmin()) {

            throw new AccessDeniedException(
                    "You cannot update another user's account");
        }

        Users existingUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));


        if(updatedUser.getMobileNumber() != null){
            existingUser.setMobileNumber(updatedUser.getMobileNumber());
        }

        if(updatedUser.getEmail() != null){
            existingUser.setEmail(updatedUser.getEmail());
        }
                Users saved =  userRepo.save(existingUser);
                return mapToDTO(saved);
    }



    @Transactional
    public String deleteUserById(int id ){

        int currentUserId = SecurityUtils.getCurrentUserId();

        if (currentUserId != id &&
                !SecurityUtils.isAdmin()) {

            throw new AccessDeniedException(
                    "You cannot delete another user's account");
        }
         Users user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));

            userRepo.delete(user);
            return "User deleted successfully";



    }



    public List<UserDTO> findAllUsers(){

        return userRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }


    public UserProfileDTO userInformation(int id){
        Users user = userRepo.findById(id).orElseThrow(()-> new RuntimeException());
        UserProfileDTO userProfileDTO = new UserProfileDTO();

        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setMobileNUmber(user.getMobileNumber());

        return  userProfileDTO;
    }


    }



