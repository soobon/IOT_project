package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String sensorType;

    private String sensorValue;

    @Column(name = "time")
    @CreationTimestamp
    private LocalDateTime time;

//    @Column(name = "description")
//    private String description;

    @Column(name = "owner")
    private String owner;

    @Column(name = "room")
    private String room;
}
