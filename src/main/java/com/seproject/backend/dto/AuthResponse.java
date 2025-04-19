package com.seproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
public class AuthResponse {
    String token;
    String username;
    String text;
}
