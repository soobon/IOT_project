package com.seproject.backend.service;

import com.seproject.backend.entity.*;

import java.util.List;

public interface AdafruitService {

    List<Room> getAllRoomByUserId(Integer id);

    List<Fan> getAllFanByRoomId(Integer id);
    List<Bulb> getAllBulbByRoomId(Integer id);
    List<Door> getAllDoorByRoomId(Integer id);
    List<RGB> getAllRgbByRoomId(Integer id);
}
