package com.seproject.backend.controller;

import com.seproject.backend.service.AdafruitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/web/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class WebController {

    private AdafruitService adafruitService;

    @GetMapping("room/{id}")
    public ResponseEntity<?> getAllRoomByUserId(@PathVariable Integer id){
        return new ResponseEntity<>(adafruitService.getAllRoomByUserId(id), HttpStatus.OK);
    }

    @GetMapping("fan/{id}")
    public ResponseEntity<?> getAllFanByRoomId(@PathVariable Integer id){
        return new ResponseEntity<>(adafruitService.getAllFanByRoomId(id), HttpStatus.OK);
    }

    @GetMapping("bulb/{id}")
    public ResponseEntity<?> getAllBulbByRoomId(@PathVariable Integer id){
        return new ResponseEntity<>(adafruitService.getAllBulbByRoomId(id), HttpStatus.OK);
    }

    @GetMapping("rgb/{id}")
    public ResponseEntity<?> getAllRgbByRoomId(@PathVariable Integer id){
        return new ResponseEntity<>(adafruitService.getAllRgbByRoomId(id), HttpStatus.OK);
    }

    @GetMapping("door/{id}")
    public ResponseEntity<?> getAllDoorByRoomId(@PathVariable Integer id){
        return new ResponseEntity<>(adafruitService.getAllDoorByRoomId(id), HttpStatus.OK);
    }

//    @GetMapping("light")
//    public ResponseEntity<?> getLightStatistic(@RequestParam LocalDate time){
//        return new ResponseEntity<>(adafruitService.getAllSensorLogBySensorType("light",time), HttpStatus.OK);
//    }
//
//    @GetMapping("temp")
//    public ResponseEntity<?> getTempStatistic(@RequestParam LocalDate time){
//        return new ResponseEntity<>(adafruitService.getAllSensorLogBySensorType("temperature",time), HttpStatus.OK);
//    }
//
//    @GetMapping("humid")
//    public ResponseEntity<?> getHumidStatistic(@RequestParam LocalDate time){
//        return new ResponseEntity<>(adafruitService.getAllSensorLogBySensorType("humid",time), HttpStatus.OK);
//    }

    @GetMapping("statistic")
    public ResponseEntity<?> getStatistic(
            @RequestParam LocalDate time,
            @RequestParam String type
    ){
        return new ResponseEntity<>(adafruitService.getAllSensorLogBySensorType(type,time), HttpStatus.OK);
    }

    @GetMapping("usage")
    public ResponseEntity<?> getDeviceUsage(@RequestParam String deviceType, @RequestParam LocalDate time){
        return new ResponseEntity<>(adafruitService.getAllDeviceLogByDeviceType(deviceType,time), HttpStatus.OK);
    }
}
