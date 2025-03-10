package com.seproject.backend.repository;

import com.seproject.backend.entity.Fan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FanRepository extends JpaRepository<Fan,Integer> {
}
