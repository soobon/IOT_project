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
        name = "devicelog"
)
public class DeviceLog {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String deviceName;

    private String deviceType;

    private String value;

    @Column(name = "time")
    @CreationTimestamp
    private LocalDateTime time;

    @Column(name = "description")
    private String description;

    @Column(name = "owner")
    private String owner;
}
