package com.krupenko.MonitorSensors.config;

import com.krupenko.MonitorSensors.constanst.CookieParams;
import com.krupenko.MonitorSensors.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtRequestFilter jwtRequestFilter;

    private static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";
    private static final String ROLE_VIEWER = "VIEWER";
    public static final String[] COMMON_PATTERNS_FOR_ALL = {"/error"};
    public static final String[] GET_PATTERNS_FOR_ALL = {"/login", "/css/**", "/v3/api-docs/**"};
    public static final String[] POST_PATTERNS_FOR_ALL = {"/api/v1/login"};
    public static final String[] WEB_PATTERNS_FOR_ADMINISTRATOR = {"/sensors/create", "/sensors/*/update", "/sensors/*/delete"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(COMMON_PATTERNS_FOR_ALL).permitAll()
                        .requestMatchers(HttpMethod.GET, GET_PATTERNS_FOR_ALL).permitAll()
                        .requestMatchers(HttpMethod.POST, POST_PATTERNS_FOR_ALL).permitAll()
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
                .logout(logout -> logout
                        .deleteCookies(CookieParams.TOKEN))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
