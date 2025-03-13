package com.seproject.backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import com.seproject.backend.service.AdafruitService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AdafruitServiceImpl implements AdafruitService {

    private static final String ADAFRUIT_USERNAME = "soobon";
    private static final String ADAFRUIT_IO_KEY = "";
    private static final String FEED_NAME = "temperature";  // Tên feed chứa dữ liệu nhiệt độ

    @Override
    public String getTemperature() {
        String url = "https://io.adafruit.com/api/v2/" + ADAFRUIT_USERNAME + "/feeds/" + FEED_NAME + "/data?limit=1";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AIO-Key", ADAFRUIT_IO_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());

            String data = rootNode.get(0).get("value").asText();
            return data;
        } catch (Exception e) {
            return "Error parsing response";
        }
    }
}
