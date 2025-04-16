package com.example.dimerco.hawb.aehawb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dimerco.hawb.aehawb.entity.Instruction;

@Service
public class InstructionApiService {

    private final RestTemplate restTemplate;

    public InstructionApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Instruction> getInstructions(String houseId) {
        try {
            String url = "https://your.api/instructions/" + houseId;
            Instruction[] result = restTemplate.getForObject(url, Instruction[].class);
            return List.of(result);
        } catch (Exception e) {
            throw new RuntimeException("Instruction API failed", e);
        }
    }
}