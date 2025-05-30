package com.seproject.backend.scheduled;

import com.seproject.backend.entity.Scenario;
import com.seproject.backend.repository.ScenarioRepository;
import com.seproject.backend.service.AdafruitMqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class SchedulerService {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private AdafruitMqttService adafruitMqttService;

    @Scheduled(cron = "0 * * * * *") // chạy mỗi phút
    public void checkAndCallApi() {
        String currentTime = LocalTime.now().format(TIME_FORMAT);
        Integer currHour = LocalTime.now().getHour();
        Integer currMinute = LocalTime.now().getMinute();
        List<Scenario> scenarios = scenarioRepository.findAllByHourAndMinute(currHour, currMinute);
        for (Scenario scenario : scenarios) {
            if (scenario.getDevice() == "bulb") {
                if (scenario.getStatus() == "on") {
                    adafruitMqttService.lightSet("1","");
                }else{
                    adafruitMqttService.lightSet("0","");
                }
            } else if (scenario.getDevice() == "door") {
                if (scenario.getStatus() == "on") {
                    adafruitMqttService.doorSet("1","", "");
                }else{
                    adafruitMqttService.doorSet("0","", "");
                }
            } else{
                if (scenario.getStatus() == "on") {
                    adafruitMqttService.fanSpeed("50","");
                }else{
                    adafruitMqttService.fanSpeed("0","");
                }
            }
        }
    }
}

