package com.test.test.service;

import com.test.test.dto.request.LoginRequestDTO;
import com.test.test.dto.request.SignupRequest;
import com.test.test.dto.response.JwtResponseDTO;
import com.test.test.dto.response.MessageResponse;
import com.test.test.model.ERole;
import com.test.test.model.Role;
import com.test.test.model.UserAccount;
import com.test.test.repository.RoleRepository;
import com.test.test.repository.UserRepository;
import com.test.test.security.jwt.JwtUtils;
import com.test.test.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<?> authenticateUser(LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(JwtResponseDTO.builder()
                .token(jwt)
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .build());
    }

    public ResponseEntity<?> registerUser( SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserAccount user = UserAccount.builder()
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        roles.add(adminRole);

        user.setRoles(roles);
        UserAccount userCreated = userRepository.save(user);

        return ResponseEntity.ok(JwtResponseDTO.builder()
                .id(userCreated.getId())
                .email(userCreated.getEmail())
                .build());
    }

}
