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

    @Column(name = "time")
    private Date time;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "bulb_id")
    private Bulb bulb;

    @ManyToOne
    @JoinColumn(name = "door_id")
    private Door door;

    @ManyToOne
    @JoinColumn(name = "rgb_id")
    private RGB rgb;

    @ManyToOne
    @JoinColumn(name = "fan_id")
    private Fan fan;
}
