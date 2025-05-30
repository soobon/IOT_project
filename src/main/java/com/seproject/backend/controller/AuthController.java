package com.seproject.backend.controller;

import com.seproject.backend.dto.AuthRequest;
import com.seproject.backend.dto.AuthResponse;
import com.seproject.backend.dto.ChangePasswordDTO;
import com.seproject.backend.service.AdafruitMqttService;
import com.seproject.backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class AuthController {

    private AuthService authService;

    private AdafruitMqttService adafruitMqttService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);
        adafruitMqttService.startMqttClient("");
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/room")
    public ResponseEntity<String> room(@RequestParam(defaultValue = "") String room) {
        adafruitMqttService.startMqttClient(room);
        return ResponseEntity.ok("Current room");
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordDTO dto,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(authService.changePassword(dto, request));
    }
}
