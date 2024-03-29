package com.ukukhula.bursaryapi.security;


import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ukukhula.bursaryapi.services.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userService.userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder);
      return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
  }
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
    // .cors(cors -> cors.disable())
    .csrf(csrf -> csrf 
      .disable()
    )
    .sessionManagement(session -> session
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    )
    .authorizeHttpRequests(authorize -> authorize
    .requestMatchers(HttpMethod.POST, "/Oauth/login").permitAll()
    // .requestMatchers(HttpMethod.GET, "/Oauth/login").permitAll()
    .requestMatchers(HttpMethod.GET, "/swagger-ui").permitAll()
    .requestMatchers(
      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/swagger-ui.html").permitAll()
    .anyRequest().authenticated()
  )
    .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
//   @Bean
// CorsConfigurationSource corsConfigurationSource() {
// 	CorsConfiguration configuration = new CorsConfiguration();
// 	configuration.setAllowedOrigin(Arrays.asList("http://localhost:8080/");
// 	configuration.setAllowedMethods(Arrays.asList("GET","POST"));
// 	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
// 	source.registerCorsConfiguration("/**", configuration);
// 	return source;
// }
}
