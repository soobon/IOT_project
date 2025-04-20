package com.seproject.backend.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Builder
@AllArgsConstructor
@Data
public class LogEvent {
    private String owner;
    private String room;
    private String type;
    private String value;
    private Boolean isDevice;
}
