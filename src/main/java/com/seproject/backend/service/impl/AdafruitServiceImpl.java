package com.seproject.backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
public class AdafruitServiceImpl implements AdafruitService {

    private RoomRepository roomRepository;

    private FanRepository fanRepository;

    private BulbRepository bulbRepository;

    private DoorRepository doorRepository;

    private RGBRepository rgbRepository;

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
}
