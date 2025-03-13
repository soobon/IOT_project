package com.seproject.backend.repository;

import com.seproject.backend.entity.Bulb;
import com.seproject.backend.entity.Fan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BulbRepository extends JpaRepository<Bulb,Integer> {
    List<Bulb> findAllByRoomId(Integer id);
}
