package com.seproject.backend.service;

import com.seproject.backend.dto.AuthRequest;
import com.seproject.backend.dto.AuthResponse;
import com.seproject.backend.entity.User;
import com.seproject.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    private UserRepository userRepository;

//    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    public AuthResponse login(AuthRequest request){

        if (userRepository.findByUsername(request.getUsername()).isEmpty())
            return new AuthResponse(null,null,"Invalid Username: Can't find Username");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return new AuthResponse(null,null, "Invalid Password: Wrong password");
        }

        //add user information to SecurityContextHolder
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, null
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);


        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Cant find user")
        );

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", request.getUsername());

        String jwtToken = jwtService.generateToken(extraClaims,user);
        System.out.println("token: " + jwtToken);
        return AuthResponse.builder()
                .token(jwtToken)
                .username(request.getUsername())
                .text("Login successfully!")
                .build();
    }
}
