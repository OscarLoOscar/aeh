package com.example.dimerco.hawb.aehawb.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "hawb")
public class DestinationConfig {
   private Map<String, String> destinations;
}
