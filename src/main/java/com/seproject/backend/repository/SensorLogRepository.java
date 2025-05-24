package com.seproject.backend.repository;

import com.seproject.backend.entity.SensorLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorLogRepository extends JpaRepository<SensorLog,Integer> {
    List<SensorLog> findBySensorType(String sensorType);
}
