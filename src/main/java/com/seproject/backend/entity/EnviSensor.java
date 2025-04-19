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
        name = "envisensor"
)
public class EnviSensor {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "sensorname")
    private String sensorName;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
