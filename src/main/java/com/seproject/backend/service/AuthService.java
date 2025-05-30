package com.seproject.backend.service;

import com.seproject.backend.dto.AuthRequest;
import com.seproject.backend.dto.AuthResponse;
import com.seproject.backend.dto.ChangePasswordDTO;
import com.seproject.backend.dto.PasswordDTO;
import com.seproject.backend.entity.Door;
import com.seproject.backend.entity.Room;
import com.seproject.backend.entity.User;
import com.seproject.backend.repository.DoorRepository;
import com.seproject.backend.repository.RoomRepository;
import com.seproject.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private RoomRepository roomRepository;

    private DoorRepository doorRepository;

    private AdafruitMqttService adafruitMqttService;

    public PasswordDTO changePassword(ChangePasswordDTO dto, HttpServletRequest request) {
        String username = request.getHeader("username");

        System.out.println("Current username: "+username);

        User user = userRepository.findByUsername(username).get();
        Room thisRoom = roomRepository.findAllByUserId(user.getId()).stream().findFirst().get();
        Door thisDoor = doorRepository.findAllByRoomId(thisRoom.getId()).stream().findFirst().get();

        String doorPassword = thisDoor.getDoorPassword();
        if (!dto.getOldPassword().equals(doorPassword)) {
            throw new RuntimeException("Old password does not match");
        }
        if (dto.getNewPassword().equals(dto.getConfirmPassword())) {
            thisDoor.setDoorPassword(dto.getNewPassword());
            doorRepository.save(thisDoor);
            adafruitMqttService.changePassword(dto.getNewPassword(), "");
        }else{
            throw new RuntimeException("New password does not match");
        }
        return new PasswordDTO(dto.getNewPassword());
    }

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
