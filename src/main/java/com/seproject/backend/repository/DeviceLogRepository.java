package com.seproject.backend.repository;

import com.seproject.backend.entity.DeviceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceLogRepository extends JpaRepository<DeviceLog,Integer> {
    List<DeviceLog> findAllByDeviceType(String deviceType);
}
