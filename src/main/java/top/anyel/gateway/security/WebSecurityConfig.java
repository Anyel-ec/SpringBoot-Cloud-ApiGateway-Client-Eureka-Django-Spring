package top.anyel.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.anyel.gateway.security.config.UserDetailsServiceImpl;
import top.anyel.gateway.security.jwt.JwtEntryPoint;
import top.anyel.gateway.security.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

  @Autowired
  UserDetailsServiceImpl userDetailsServiceImpl;

  @Autowired
  JwtEntryPoint jwtEntryPoint;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtTokenFilter jwtTokenFilter;

  AuthenticationManager authenticationManager;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    authenticationManager = builder.build();
    http.authenticationManager(authenticationManager);

    http.csrf(AbstractHttpConfigurer::disable);
    http.cors(Customizer.withDefaults());
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // Require authentication for all requests
    http.authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated());

    http.exceptionHandling(exc -> exc.authenticationEntryPoint(jwtEntryPoint));
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
