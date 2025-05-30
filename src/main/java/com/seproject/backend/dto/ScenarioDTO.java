package com.seproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ScenarioDTO {
    Integer hour;
    Integer minute;
    String device;
    String status;
    String name;
}
