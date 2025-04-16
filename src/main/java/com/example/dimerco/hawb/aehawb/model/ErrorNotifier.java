package com.example.dimerco.hawb.aehawb.model;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ErrorNotifier {

    private final RestTemplate restTemplate = new RestTemplate(); 

    public void send(String step, String context, String message) {
        String payload = String.format("Step: %s\nContext: %s\nError: %s", step, context, message);
        String webhookUrl = "https://your.power.automate.url";
        restTemplate.postForEntity(webhookUrl, payload, String.class);
    }
}