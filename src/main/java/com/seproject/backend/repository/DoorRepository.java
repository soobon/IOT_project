package com.seproject.backend.repository;

import com.seproject.backend.entity.Door;
import com.seproject.backend.entity.Fan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoorRepository extends JpaRepository<Door,Integer> {
    List<Door> findAllByRoomId(Integer id);
}
