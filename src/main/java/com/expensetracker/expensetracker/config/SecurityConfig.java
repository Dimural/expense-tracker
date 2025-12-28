package com.expensetracker.expensetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // for now, turn off CSRF so it's easier to test with Postman
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // allow anyone to hit auth endpoints
                        .requestMatchers("/auth/**").permitAll()
                        // for now, allow everything else too while developing
                        .anyRequest().permitAll()
                )
                // disable the default login page/form
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
