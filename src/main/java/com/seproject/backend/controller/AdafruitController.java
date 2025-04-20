package com.seproject.backend.controller;

import com.seproject.backend.service.AdafruitMqttService;
import com.seproject.backend.service.AdafruitService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adafruit/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
class AdafruitController {

    @Autowired
    private AdafruitMqttService adafruitMqttService;

    @PostMapping("/light/on")
    public ResponseEntity<String> lightOn(@RequestParam(defaultValue = "") String room){
        adafruitMqttService.lightSet("1",room);
        return new ResponseEntity<>("Light is on!",HttpStatus.OK);
    }

    @PostMapping("/light/off")
    public ResponseEntity<String> lightOff(@RequestParam(defaultValue = "") String room){
        adafruitMqttService.lightSet("0",room);
        return new ResponseEntity<>("Light is off!",HttpStatus.OK);
    }

//    @PostMapping("/fan/on")
//    public ResponseEntity<String> fanOn(){
//        adafruitMqttService.fanSet("1");
//        return new ResponseEntity<>("Fan is on! with 50% speed",HttpStatus.OK);
//    }
//
//    @PostMapping("/fan/off")
//    public ResponseEntity<String> fanOff(){
//        adafruitMqttService.fanSet("0");
//        return new ResponseEntity<>("Fan is off!",HttpStatus.OK);
//    }

    @PostMapping("/fan/on/{speed}")
    public ResponseEntity<String> fanSpeed(@PathVariable String speed, @RequestParam(defaultValue = "") String room){
        adafruitMqttService.fanSpeed(speed,room);
        return new ResponseEntity<>("Fan is on! with " + speed + "% speed",HttpStatus.OK);
    }

//    @PostMapping("/feed/{value}")
//    public ResponseEntity<String> createFeed(@PathVariable String value, @RequestParam(defaultValue = "") String room){
//        adafruitMqttService.createFeed("20",room);
//        return new ResponseEntity<>("New feed created!",HttpStatus.OK);
//    }

    @PostMapping("/rgb/{value}")
    public ResponseEntity<String> setRGBColor(@PathVariable String value, @RequestParam(defaultValue = "") String room){
        adafruitMqttService.colorSet('#'+value,room);
        return new ResponseEntity<>("Color set!",HttpStatus.OK);
    }

    @PostMapping("/door/open")
    public ResponseEntity<String> doorOpen(@RequestParam(defaultValue = "") String room){
        adafruitMqttService.doorSet("1",room);
        return new ResponseEntity<>("Door is opened!",HttpStatus.OK);
    }

    @PostMapping("/door/close")
    public ResponseEntity<String> doorClose(@RequestParam(defaultValue = "") String room){
        adafruitMqttService.doorSet("0",room);
        return new ResponseEntity<>("Door is closed!",HttpStatus.OK);
    }

}
