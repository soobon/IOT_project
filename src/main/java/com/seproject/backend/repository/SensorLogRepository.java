package com.seproject.backend.repository;

import com.seproject.backend.entity.SensorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorLogRepository extends JpaRepository<SensorLog,Integer> {
}
