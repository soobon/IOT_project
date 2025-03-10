package com.seproject.backend.repository;

import com.seproject.backend.entity.DeviceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceLogRepository extends JpaRepository<DeviceLog,Integer> {
}
