package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "lightsensor"
)
public class LightSensor {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "sensorname")
    private String sensorName;

//    @Column(name = "light")
//    private Double lightData;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
