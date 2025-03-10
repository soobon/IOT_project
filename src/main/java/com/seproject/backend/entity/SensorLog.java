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

    @Column(name = "time")
    private Date time;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "envi_id")
    private EnviSensor enviSensor;

    @ManyToOne
    @JoinColumn(name = "light_id")
    private LightSensor lightSensor;
}
