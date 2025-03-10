package com.seproject.backend.repository;

import com.seproject.backend.entity.LightSensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LightSensorRepository extends JpaRepository<LightSensor,Integer> {
}
