package com.krupenko.MonitorSensors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";
    private static final String ROLE_VIEWER = "VIEWER";
    public static final String[] PATTERNS_FOR_ALL = {"/login", "/css/**", "/v3/api-docs/**"};
    public static final String[] WEB_PATTERNS_FOR_ADMINISTRATOR = {"/sensors/create", "/sensors/*/update", "/sensors/*/delete"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PATTERNS_FOR_ALL).permitAll()
                        .requestMatchers(HttpMethod.POST, WEB_PATTERNS_FOR_ADMINISTRATOR).hasAuthority(ROLE_ADMINISTRATOR)

                        .requestMatchers(HttpMethod.GET, "/api/v1/sensors").hasAnyAuthority(ROLE_ADMINISTRATOR, ROLE_VIEWER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/sensors/*").hasAnyAuthority(ROLE_ADMINISTRATOR, ROLE_VIEWER)
                        .requestMatchers(HttpMethod.POST, "/api/v1/sensors").hasAuthority(ROLE_ADMINISTRATOR)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/sensors/*").hasAuthority(ROLE_ADMINISTRATOR)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/sensors/*").hasAuthority(ROLE_ADMINISTRATOR)

                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/sensors"))
                .build();
    }

}
