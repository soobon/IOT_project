package com.seproject.backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seproject.backend.dto.SensorLogDTO;
import com.seproject.backend.entity.*;
import com.seproject.backend.repository.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import com.seproject.backend.service.AdafruitService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
public class AdafruitServiceImpl implements AdafruitService {

    private RoomRepository roomRepository;

    private FanRepository fanRepository;

    private BulbRepository bulbRepository;

    private DoorRepository doorRepository;

    private RGBRepository rgbRepository;

    private SensorLogRepository sensorLogRepository;

    private DeviceLogRepository deviceLogRepository;

    @Override
    public List<Room> getAllRoomByUserId(Integer id) {
        return roomRepository.findAllByUserId(id);
    }

    @Override
    public List<Fan> getAllFanByRoomId(Integer id) {
        return fanRepository.findAllByRoomId(id);
    }

    @Override
    public List<Bulb> getAllBulbByRoomId(Integer id) {
        return bulbRepository.findAllByRoomId(id);
    }

    @Override
    public List<Door> getAllDoorByRoomId(Integer id) {
        return doorRepository.findAllByRoomId(id);
    }

    @Override
    public List<RGB> getAllRgbByRoomId(Integer id) {
        return rgbRepository.findAllByRoomId(id);
    }

    @Override
    public List<SensorLogDTO> getAllSensorLogBySensorType(String sensorType, LocalDate time) {
        List<SensorLogDTO> dtos = new ArrayList<>();
        List<SensorLog> sensorLogs = sensorLogRepository.findBySensorType(sensorType);
        for (SensorLog sensorLog : sensorLogs) {
            if (!time.equals(LocalDate.from(sensorLog.getTime()))) continue;
            LocalTime hour = LocalTime.from(sensorLog.getTime());
            String value = sensorLog.getSensorValue();
            dtos.add(SensorLogDTO.builder()
                            .time(hour)
                            .value(value)
                    .build());
        }
        return dtos;
    }

    @Override
    public Integer getAllDeviceLogByDeviceType(String deviceType, LocalDate time) {
        int count = 0;
        List<DeviceLog> deviceLogs = deviceLogRepository.findAllByDeviceType(deviceType);
        for (DeviceLog deviceLog : deviceLogs) {
            if (!time.equals(LocalDate.from(deviceLog.getTime()))) continue;
            count += 1;
        }
        return count;
    }
}
