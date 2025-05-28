package com.seproject.backend.entity;

import com.seproject.backend.entity.enum_class.Status;
import com.seproject.backend.entity.enum_class.Type;
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
        name = "door"
)
public class Door {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "devicename")
    private String deviceName;

//    @Column(name = "status")
//    private Status status;
//
//    @Column(name = "type")
//    private Type type;
//
    @Column(name = "password")
    private String doorPassword;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
