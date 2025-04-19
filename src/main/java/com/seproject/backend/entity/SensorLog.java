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
        name = "sensorlog"
)
public class SensorLog {
    @Id
    @Column(name = "id")
    private Integer id;

    private String sensorName;

    private String sensorType;

    private String sensorValue;

    @Column(name = "time")
    private Date time;

    @Column(name = "description")
    private String description;
}
