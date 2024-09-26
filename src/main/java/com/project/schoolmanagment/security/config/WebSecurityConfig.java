package com.project.schoolmanagment.security.config;

import com.project.schoolmanagment.security.jwt.AuthEntryPointJwt;
import com.project.schoolmanagment.security.jwt.AuthTokenFilter;
import com.project.schoolmanagment.security.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UserDetailServiceImpl userDetailService;
  
  private final AuthEntryPointJwt authEntryPointJwt;
  
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //CORD cross-origin resource sharing
    http.cors().and()
        .csrf().disable()
        //configure the exception handler
        .exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
        .and()
        //configure session management
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //configure allowlist
        .and()
        .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
        //other requests will be authenticated
        .anyRequest().authenticated();
    //configure the frames t be sure from the same origin
    http.headers().frameOptions().sameOrigin();
    //configure authentication provider
    http.authenticationProvider(authenticationProvider());
    //configure JWT token handling
    http.addFilterBefore(authenticationJwtTokenFilter(),
        UsernamePasswordAuthenticationFilter.class);
       return http.build(); 
  }
  
  
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }
  
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }
  
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }
  
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            //we let all sources to call our APIs
            .allowedOrigins("*")
            .allowedHeaders("*")
            .allowedMethods("*");
      }
    };
  }
  
  private static final String[] AUTH_WHITELIST = {
      "/v3/api-docs/**",
      "swagger-ui.html",
      "/swagger-ui/**",
      "/",
      "index.html",
      "/images/**",
      "/css/**",
      "/js/**",
      "/contactMessages/save",
      "/auth/login"
  };

}
