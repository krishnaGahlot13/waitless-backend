package com.waitless.backend.config;

import com.waitless.backend.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService,
                                 JwtFilter jwtFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth


                        .requestMatchers("/auth/**", "/home").permitAll()

                        // Admin-only routes
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/users/admin/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        // User routes — allow USER and ADMIN (ownership enforced in service layer)
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,    "/users/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("USER", "ADMIN")

                        // Business routes
                        .requestMatchers("/business/**").hasAnyRole("BUSINESS", "ADMIN")

                        // Queue management — business owners and admins
                        .requestMatchers("/queues/**").hasAnyRole("ADMIN", "BUSINESS")

                        // Queue entries — all authenticated roles
                        .requestMatchers("/queue-entries/**").hasAnyRole("USER", "BUSINESS", "ADMIN")

                        // Services — business owners and admins
                        .requestMatchers("/services/**").hasAnyRole("ADMIN", "BUSINESS")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}