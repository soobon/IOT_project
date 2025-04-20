package com.seproject.backend.service;

import com.seproject.backend.entity.User;
import com.seproject.backend.log.LogEvent;
import com.seproject.backend.repository.UserRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

@Service
public class AdafruitMqttService {

    private String ADAFRUIT_IO_USERNAME;
    private static final String BULB_FEED = "bbc-led";//adjust light

    private static final String TEMP_FEED = "bbc-temp";//show temperature

    private static final String HUMID_FEED = "bbc-humid";//show humid

    private static final String LIGHT_FEED = "bbc-light";//show light

    private static final String RGB_COLOR_FEED = "bbc-rgb";//adjust rgb color

    private static final String FAN_STATUS_FEED = "fan_status";

    private static final String FAN_SPEED_FEED = "bbc-fan";//adjust fan speed

    private static final String DOOR_FEED = "bbc-door";

    private static final String DOOR_PASS_FEED = "door_pass";

    private static final String DOOR_REAL_PASS_FEED = "door_real_pass";
    private static final String BROKER_URL = "tcp://io.adafruit.com:1883";
    private static final String CLIENT_ID = "SmartHome";
//ok git
    private MqttClient mqttClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void startMqttClient(String room) {
        try {
            //retrieve user's information from SecurityContextHolder
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username;
            if (authentication != null && authentication.isAuthenticated()) {
                username = authentication.getName(); // Get username
                System.out.println("Username is:" + username);
            }else throw new RuntimeException("Authentication is null");

            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new RuntimeException("User not found")
            );

            this.ADAFRUIT_IO_USERNAME = username;

            final String ADAFRUIT_IO_KEY = user.getAdafruit();

            mqttClient = new MqttClient(BROKER_URL, CLIENT_ID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(ADAFRUIT_IO_USERNAME);
            options.setPassword(ADAFRUIT_IO_KEY.toCharArray());

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Mất kết nối MQTT!");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());

                    //temperature
                    if (topic.endsWith(room+TEMP_FEED)) {
                        eventPublisher.publishEvent(
                                LogEvent.builder()
                                        .owner(user.getUsername())
                                        .isDevice(false)
                                        .room(room)
                                        .type("temperature")
                                        .value(payload)
                                        .build()
                        );

                        System.out.println("🔥 Nhiệt độ mới: " + payload);
                        messagingTemplate.convertAndSend("/topic/temperature", payload);
                    }
                    //led-bulb
                    if (topic.endsWith(room+BULB_FEED)) {
                        eventPublisher.publishEvent(
                                LogEvent.builder()
                                        .owner(user.getUsername())
                                        .isDevice(true)
                                        .room(room)
                                        .type("bulb")
                                        .value(payload)
                                        .build()
                        );

                        System.out.println("\uD83D\uDCA1 Trạng thái đèn: " + payload);
                        messagingTemplate.convertAndSend("/topic/bulb", payload);
                    }
                    //humid
                    if (topic.endsWith(room+HUMID_FEED)) {
                        eventPublisher.publishEvent(
                                LogEvent.builder()
                                        .owner(user.getUsername())
                                        .isDevice(false)
                                        .room(room)
                                        .type("humid")
                                        .value(payload)
                                        .build()
                        );

                        System.out.println("\uD83D\uDCA6 Độ ẩm: " + payload);
                        messagingTemplate.convertAndSend("/topic/humid", payload);
                    }
                    //light
                    if (topic.endsWith(room+LIGHT_FEED)) {
                        eventPublisher.publishEvent(
                                LogEvent.builder()
                                        .owner(user.getUsername())
                                        .isDevice(false)
                                        .room(room)
                                        .type("light")
                                        .value(payload)
                                        .build()
                        );

                        System.out.println("✨ Ánh sáng: " + payload);
                        messagingTemplate.convertAndSend("/topic/light", payload);
                    }
                    //fan-fan_speed
                    if (topic.endsWith(room+FAN_SPEED_FEED)) {
                        eventPublisher.publishEvent(
                                LogEvent.builder()
                                        .owner(user.getUsername())
                                        .isDevice(true)
                                        .room(room)
                                        .type("fan-speed")
                                        .value(payload)
                                        .build()
                        );

                        System.out.println("\uD83D\uDCA8 Tốc độ quạt: " + payload);
                        messagingTemplate.convertAndSend("/topic/fan-speed", payload);
                    }
                    //door
                    if (topic.endsWith(room+DOOR_FEED)){

                        eventPublisher.publishEvent(
                                LogEvent.builder()
                                        .owner(user.getUsername())
                                        .isDevice(true)
                                        .room(room)
                                        .type("door")
                                        .value(payload)
                                        .build()
                        );

                        System.out.println("\uD83D\uDEAA  Trạng thái cửa: " + payload);
                        messagingTemplate.convertAndSend("/topic/door", payload);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            mqttClient.connect(options);

            final String roomFeed = ADAFRUIT_IO_USERNAME + "/feeds/" + room;

            mqttClient.subscribe(roomFeed + BULB_FEED);
            mqttClient.subscribe(roomFeed + TEMP_FEED);
            mqttClient.subscribe(roomFeed + HUMID_FEED);
            mqttClient.subscribe(roomFeed + LIGHT_FEED);
            mqttClient.subscribe(roomFeed + FAN_STATUS_FEED);
            mqttClient.subscribe(roomFeed + FAN_SPEED_FEED);
            mqttClient.subscribe(roomFeed + DOOR_FEED);
            mqttClient.subscribe(roomFeed + DOOR_PASS_FEED);
            mqttClient.subscribe(roomFeed + DOOR_REAL_PASS_FEED);
            mqttClient.subscribe(roomFeed + RGB_COLOR_FEED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lightSet(String value, String room) {
        try {
            String topic = ADAFRUIT_IO_USERNAME + "/feeds/" + room + BULB_FEED;
            MqttMessage message = new MqttMessage(value.getBytes());
            mqttClient.publish(topic, message);
            System.out.println("📤 Đã gửi dữ liệu bật/tắt bóng đèn: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doorSet(String value, String room) {
        try {
            String topic = ADAFRUIT_IO_USERNAME + "/feeds/" + room + DOOR_FEED;
            MqttMessage message = new MqttMessage(value.getBytes());
            mqttClient.publish(topic, message);
            System.out.println("📤 Đã gửi dữ liệu đóng/mở cửa: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fanSpeed(String value, String room) {
        try {
            String topic = ADAFRUIT_IO_USERNAME + "/feeds/" + room + FAN_SPEED_FEED;
            MqttMessage message = new MqttMessage(value.getBytes());
            mqttClient.publish(topic, message);
            System.out.println("📤 Đã gửi dữ liệu tốc độ quạt: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void colorSet(String value, String room) {
        try {
            String topic = ADAFRUIT_IO_USERNAME + "/feeds/" + room + RGB_COLOR_FEED;
            MqttMessage message = new MqttMessage(value.getBytes());
            mqttClient.publish(topic, message);
            System.out.println("📤 Đã gửi dữ liệu màu sắc: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void createFeed(String value, String room) {
//        try {
//            String topic = ADAFRUIT_IO_USERNAME + "/feeds/" + room + "fan-room2";
//            MqttMessage message = new MqttMessage(value.getBytes());
//            mqttClient.publish(topic, message);
//            System.out.println("📤 Tạo feed: " + value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
