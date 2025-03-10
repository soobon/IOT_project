package com.seproject.backend.repository;

import com.seproject.backend.entity.Bulb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulbRepository extends JpaRepository<Bulb,Integer> {
}
