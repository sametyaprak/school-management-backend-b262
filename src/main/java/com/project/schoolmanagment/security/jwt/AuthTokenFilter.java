package com.project.schoolmanagment.security.jwt;

import com.project.schoolmanagment.security.service.UserDetailServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);
  
  @Autowired
  private JwtUtils jwtUtils;
  
  @Autowired
  private UserDetailServiceImpl userDetailService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      // 1-from every request, we will get JWT
      String jwt = parseJwt(request);
      // 2- validate JWT
      if(jwt != null && jwtUtils.validateToken(jwt)) {
        // 3- we need username to get the data
        String username = jwtUtils.getUsernameFromToken(jwt);
        // 4-check DB and fetch user and upgrade it to User details
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        // 5- we are setting attribute property with username information
        request.setAttribute("username", username);
        // 6- we load user details information to security context
        UsernamePasswordAuthenticationToken authentication = 
            new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (UsernameNotFoundException e) {
      LOGGER.error("Cannot set user authentication", e);
    }
    filterChain.doFilter(request, response);
  }
  
  
  private String parseJwt(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}
