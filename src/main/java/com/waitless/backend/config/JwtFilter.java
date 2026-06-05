package com.waitless.backend.config;

import com.waitless.backend.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.startsWith("/auth") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/swagger-ui.html")) {

            filterChain.doFilter(request, response);
            return;
        }


        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("JWT token missing for path: {}", path);

            sendUnauthorized(response, "JWT token missing");

            return;
        }

        String token = authHeader.substring(7);

        String username;

        try {

            username = jwtUtil.extractUsername(token);

        } catch (Exception e) {

            log.debug("JWT token invalid or expired for path: {}", path);

            sendUnauthorized(response, "Invalid or expired JWT token");

            return;
        }

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            try {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                // FIX: explicitly reject if validateToken returns false
                if (!jwtUtil.validateToken(token, userDetails.getUsername())) {

                    log.debug("JWT token validation failed for user: {}", username);

                    sendUnauthorized(response, "Token validation failed");

                    return;
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);

                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("AUTH SET FOR USER = " + username);


            } catch (UsernameNotFoundException e) {

                log.debug("User not found for JWT token: {}", username);

                sendUnauthorized(response, "Invalid authentication token");

                return;
            }
        }

        System.out.println("FILTER PASSED FOR PATH = " + path); // ADD THIS
        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(
            HttpServletResponse response,
            String message
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");

        response.getWriter()
                .write("{\"error\":\"" + message + "\"}");
    }
}