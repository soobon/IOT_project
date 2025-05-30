package com.seproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ChangePasswordDTO {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
