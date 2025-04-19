package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "devicelog"
)
public class DeviceLog {
    @Id
    @Column(name = "id")
    private Integer id;

    private String deviceName;

    private String deviceType;

    private String value;

    @Column(name = "time")
    private Date time;

    @Column(name = "description")
    private String description;
}
