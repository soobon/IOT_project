package com.seproject.backend.log;

import com.seproject.backend.entity.DeviceLog;
import com.seproject.backend.entity.SensorLog;
import com.seproject.backend.repository.DeviceLogRepository;
import com.seproject.backend.repository.SensorLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogListener {
    private DeviceLogRepository deviceLogRepository;

    private SensorLogRepository sensorLogRepository;

    @EventListener
    public void handleLoggingEvent(LogEvent event) {
        System.out.println(event);
        if (event.getIsDevice()){
            DeviceLog deviceLog = DeviceLog.builder()
                    .room(event.getRoom())
                    .owner(event.getOwner())
                    .value(event.getValue())
                    .deviceType(event.getType())
                    .build();
            deviceLogRepository.save(deviceLog);
        }else{
            SensorLog sensorLog = SensorLog.builder()
                    .room(event.getRoom())
                    .owner(event.getOwner())
                    .sensorValue(event.getValue())
                    .sensorType(event.getType())
                    .build();
            sensorLogRepository.save(sensorLog);
        }
    }
}
