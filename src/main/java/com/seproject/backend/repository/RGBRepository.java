package com.seproject.backend.repository;

import com.seproject.backend.entity.Door;
import com.seproject.backend.entity.Fan;
import com.seproject.backend.entity.RGB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RGBRepository extends JpaRepository<RGB,Integer> {
    List<RGB> findAllByRoomId(Integer id);
}
