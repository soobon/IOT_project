package com.seproject.backend.repository;

import com.seproject.backend.entity.Fan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FanRepository extends JpaRepository<Fan,Integer> {
    List<Fan> findAllByRoomId(Integer id);
}
