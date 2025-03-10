package com.seproject.backend.repository;

import com.seproject.backend.entity.Door;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorRepository extends JpaRepository<Door,Integer> {
}
