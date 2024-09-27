package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.payload.mappers.UserMapper;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.request.authentication.LoginRequest;
import com.project.schoolmanagment.payload.request.authentication.UpdatePasswordRequest;
import com.project.schoolmanagment.payload.response.authentication.LoginResponse;
import com.project.schoolmanagment.repository.user.UserRepository;
import com.project.schoolmanagment.security.jwt.JwtUtils;
import com.project.schoolmanagment.security.service.UserDetailsImpl;
import com.project.schoolmanagment.service.helper.MethodHelper;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final MethodHelper methodHelper;

  public LoginResponse authenticateUser(LoginRequest loginRequest) {
    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();
    //injection of security authentication in service
    Authentication authentication = 
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username,password));
    //validated authentication info is upload to security context
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    String token = jwtUtils.generateToken(authentication);
    //get all info for user
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    Set<String>roles = userDetails.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());
    
    return LoginResponse.builder()
        .token(token)
        .username(username)
        .ssn(userDetails.getSsn())
        .name(userDetails.getName())
        .role(roles.stream().findFirst().get())
        .build();
  }

  public void updatePassword(UpdatePasswordRequest updatePasswordRequest,
      HttpServletRequest httpServletRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User user = userRepository.findByUsername(username);
    methodHelper.checkBuildIn(user);
    //validate old password is correct
    if(passwordEncoder.matches(updatePasswordRequest.getNewPassword(),user.getPassword())){
      throw new BadRequestException(ErrorMessages.PASSWORD_SHOULD_NOT_MATCHED);
    }
    user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
    userRepository.save(user);    
  }
}
