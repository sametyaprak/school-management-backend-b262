package com.project.schoolmanagment.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
  
  private Long id;
  
  private String username;
  
  @JsonIgnore
  private String password;
  
  private String name;
  
  private Boolean isAdvisor;
  
  private String ssn;
  
  private List<GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String name, Boolean isAdvisor, String password,
      String ssn, String role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.name = name;
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role));
    this.authorities = authorities;
    this.isAdvisor = isAdvisor;
    this.ssn = ssn;    
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
