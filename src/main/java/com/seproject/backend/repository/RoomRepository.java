package com.seproject.backend.repository;

import com.seproject.backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    List<Room> findAllByUserId(Integer id);
}
