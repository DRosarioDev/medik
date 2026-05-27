package com.rosariodev.medik.filter;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rosariodev.medik.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthenticationFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter(customUserDetailsService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    @SuppressWarnings("removal")
    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/utenti/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/medici/pazienti").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/medici/*/ricette").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/pazienti/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/pazienti").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/pazienti/*/nuova-ricetta").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/appuntamenti").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/appuntamenti/nuovo").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/appuntamenti/disponibilita").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/appuntamenti/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/appuntamenti/elimina/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/appuntamenti/prenotazione-vicina").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/paziente/ricette").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/paziente/medico").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/paziente/cambio-medico").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/paziente/medici-disponibili").authenticated()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
