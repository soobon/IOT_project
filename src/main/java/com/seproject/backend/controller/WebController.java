package com.seproject.backend.controller;

import com.seproject.backend.service.AdafruitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
