package com.seproject.backend.service;

import com.seproject.backend.dto.ScenarioDTO;
import com.seproject.backend.dto.SensorLogDTO;
import com.seproject.backend.entity.*;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AdafruitService {

    List<Room> getAllRoomByUserId(Integer id);

    List<Fan> getAllFanByRoomId(Integer id);
    List<Bulb> getAllBulbByRoomId(Integer id);
    List<Door> getAllDoorByRoomId(Integer id);
    List<RGB> getAllRgbByRoomId(Integer id);

    List<SensorLogDTO> getAllSensorLogBySensorType(String sensorType, LocalDate time);

    Integer getAllDeviceLogByDeviceType(String deviceType, LocalDate time);

    List<Scenario> getAllScenario();

    Scenario addScenario(ScenarioDTO dto, HttpServletRequest request);

    Scenario deleteScenario(Integer id);

    List<DeviceLog> getAllDeviceLog(LocalDate time);
}
