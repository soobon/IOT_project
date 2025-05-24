package com.seproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class SensorLogDTO {
    private LocalTime timestamp;
    private String value;
}
