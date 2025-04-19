package com.seproject.backend.service;

import com.seproject.backend.entity.User;
import com.seproject.backend.repository.UserRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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

//    public AdafruitMqttService() {
//        try {
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username;
//            if (authentication != null && authentication.isAuthenticated()) {
//                username = authentication.getName(); // Lấy tên người dùng
//                System.out.println(username);
//            }else throw new RuntimeException("Authentication is null");
//
//            User user = userRepository.findByUsername(username).orElseThrow(
//                    () -> new RuntimeException("User not found")
//            );
//
//            this.ADAFRUIT_IO_USERNAME = username;
//
//            final String ADAFRUIT_IO_KEY = user.getAdafruit();
//
//            mqttClient = new MqttClient(BROKER_URL, CLIENT_ID);
//            MqttConnectOptions options = new MqttConnectOptions();
//            options.setUserName(ADAFRUIT_IO_USERNAME);
//            options.setPassword(ADAFRUIT_IO_KEY.toCharArray());
//
//            mqttClient.setCallback(new MqttCallback() {
//                @Override
//                public void connectionLost(Throwable cause) {
//                    System.out.println("Mất kết nối MQTT!");
//                }
//
//                @Override
//                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    String payload = new String(message.getPayload());
//                    if (topic.endsWith(TEMP_FEED)) {
//                        System.out.println("🔥 Nhiệt độ mới: " + payload);
//                        messagingTemplate.convertAndSend("/topic/temperature", payload);
//                    }
//                    if (topic.endsWith(BULB_FEED)) {
//                        System.out.println("\uD83D\uDCA1 Trạng thái đèn: " + payload);
//                        messagingTemplate.convertAndSend("/topic/bulb", payload);
//                    }
//                    if (topic.endsWith(HUMID_FEED)) {
//                        System.out.println("\uD83D\uDCA6 Độ ẩm: " + payload);
//                        messagingTemplate.convertAndSend("/topic/humid", payload);
//                    }
//                    if (topic.endsWith(LIGHT_FEED)) {
//                        System.out.println("✨ Ánh sáng: " + payload);
//                        messagingTemplate.convertAndSend("/topic/light", payload);
//                    }
//                    if (topic.endsWith(FAN_STATUS_FEED)) {
//                        String topic_speed = ADAFRUIT_IO_USERNAME + "/feeds/" + FAN_SPEED_FEED;
//                        if (payload.equals("1")) {
//                            MqttMessage speed_message = new MqttMessage("50".getBytes());
//                            mqttClient.publish(topic_speed, speed_message);
//
//                            System.out.println("\uD83D\uDCA8 Trạng thái quạt: " + payload);
//                            System.out.println("\uD83D\uDCA8 Tốc độ quạt: " + 50);
//                        }else{
//                            MqttMessage speed_message = new MqttMessage("0".getBytes());
//                            mqttClient.publish(topic_speed, speed_message);
//
//                            System.out.println("\uD83D\uDCA8 Trạng thái quạt: " + payload);
//                            System.out.println("\uD83D\uDCA8 Tốc độ quạt: " + 0);
//                        }
//                    }
//                    if (topic.endsWith(FAN_SPEED_FEED)) {
//                        System.out.println("\uD83D\uDCA8 Tốc độ quạt: " + payload);
//                    }
//
//                    // Gửi dữ liệu đến frontend nếu cần
//                }
//
//                @Override
//                public void deliveryComplete(IMqttDeliveryToken token) {
//                }
//            });
//
//            mqttClient.connect(options);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + BULB_FEED);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + TEMP_FEED);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + HUMID_FEED);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + LIGHT_FEED);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + FAN_STATUS_FEED);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + FAN_SPEED_FEED);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + DOOR_FEED);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + DOOR_PASS_FEED);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + DOOR_REAL_PASS_FEED);
//            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + RGB_COLOR_FEED);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void startMqttClient() {
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
                    if (topic.endsWith(TEMP_FEED)) {
                        System.out.println("🔥 Nhiệt độ mới: " + payload);
                        messagingTemplate.convertAndSend("/topic/temperature", payload);
                    }
                    if (topic.endsWith(BULB_FEED)) {
                        System.out.println("\uD83D\uDCA1 Trạng thái đèn: " + payload);
                        messagingTemplate.convertAndSend("/topic/bulb", payload);
                    }
                    if (topic.endsWith(HUMID_FEED)) {
                        System.out.println("\uD83D\uDCA6 Độ ẩm: " + payload);
                        messagingTemplate.convertAndSend("/topic/humid", payload);
                    }
                    if (topic.endsWith(LIGHT_FEED)) {
                        System.out.println("✨ Ánh sáng: " + payload);
                        messagingTemplate.convertAndSend("/topic/light", payload);
                    }
//                    if (topic.endsWith(FAN_STATUS_FEED)) {
//                        String topic_speed = ADAFRUIT_IO_USERNAME + "/feeds/" + FAN_SPEED_FEED;
//                        if (payload.equals("1")) {
//                            MqttMessage speed_message = new MqttMessage("50".getBytes());
//                            mqttClient.publish(topic_speed, speed_message);
//
//                            System.out.println("\uD83D\uDCA8 Trạng thái quạt: " + payload);
//                            System.out.println("\uD83D\uDCA8 Tốc độ quạt: " + 50);
//                        }else{
//                            MqttMessage speed_message = new MqttMessage("0".getBytes());
//                            mqttClient.publish(topic_speed, speed_message);
//
//                            System.out.println("\uD83D\uDCA8 Trạng thái quạt: " + payload);
//                            System.out.println("\uD83D\uDCA8 Tốc độ quạt: " + 0);
//                        }
//                    }
                    if (topic.endsWith(FAN_SPEED_FEED)) {
                        System.out.println("\uD83D\uDCA8 Tốc độ quạt: " + payload);
                    }

                    // Gửi dữ liệu đến frontend nếu cần
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            mqttClient.connect(options);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + BULB_FEED);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + TEMP_FEED);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + HUMID_FEED);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + LIGHT_FEED);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + FAN_STATUS_FEED);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + FAN_SPEED_FEED);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + DOOR_FEED);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + DOOR_PASS_FEED);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + DOOR_REAL_PASS_FEED);
            mqttClient.subscribe(ADAFRUIT_IO_USERNAME + "/feeds/" + RGB_COLOR_FEED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lightSet(String value) {
        try {
            String topic = ADAFRUIT_IO_USERNAME + "/feeds/" + BULB_FEED;
            MqttMessage message = new MqttMessage(value.getBytes());
            mqttClient.publish(topic, message);
            System.out.println("📤 Đã gửi dữ liệu bật/tắt bóng đèn: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void fanSet(String value) {
//        try {
//            String topic_status = ADAFRUIT_IO_USERNAME + "/feeds/" + FAN_STATUS_FEED;
//            String topic_speed = ADAFRUIT_IO_USERNAME + "/feeds/" + FAN_SPEED_FEED;
//
//            MqttMessage status_message = new MqttMessage(value.getBytes());
//            mqttClient.publish(topic_status, status_message);
//            System.out.println("📤 Đã gửi dữ liệu bật/tắt quạt: " + value);
//
//            if (value.equals("1")){
//                MqttMessage speed_message = new MqttMessage("50".getBytes());
//                mqttClient.publish(topic_speed, speed_message);
//                System.out.println("📤 Đã gửi dữ liệu tốc độ quạt: " + 50);
//            }else{
//                MqttMessage speed_message = new MqttMessage("0".getBytes());
//                mqttClient.publish(topic_speed, speed_message);
//                System.out.println("📤 Đã gửi dữ liệu tốc độ quạt: " + 0);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void fanSpeed(String value) {
        try {
            String topic = ADAFRUIT_IO_USERNAME + "/feeds/" + FAN_SPEED_FEED;
            MqttMessage message = new MqttMessage(value.getBytes());
            mqttClient.publish(topic, message);
            System.out.println("📤 Đã gửi dữ liệu tốc độ quạt: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void colorSet(String value) {
        try {
            String topic = ADAFRUIT_IO_USERNAME + "/feeds/" + RGB_COLOR_FEED;
            MqttMessage message = new MqttMessage(value.getBytes());
            mqttClient.publish(topic, message);
            System.out.println("📤 Đã gửi dữ liệu màu sắc: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFeed(String value) {
        try {
            String topic = ADAFRUIT_IO_USERNAME + "/feeds/" + "fan-room2";
            MqttMessage message = new MqttMessage(value.getBytes());
            mqttClient.publish(topic, message);
            System.out.println("📤 Tạo feed: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
