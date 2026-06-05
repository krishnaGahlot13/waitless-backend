package com.waitless.backend.security;

import com.waitless.backend.model.Roles;
import com.waitless.backend.model.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {



    public static Users getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        return principal.getUsers();
    }

    public static int getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public static boolean isAdmin() {
        return getCurrentUser()
                .getRole()
                .name()
                .equals("ADMIN");
    }

    public static boolean isBusiness() {
        return getCurrentUser().getRole() == Roles.BUSINESS;
    }
    
}