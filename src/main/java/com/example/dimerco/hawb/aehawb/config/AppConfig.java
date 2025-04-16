package com.example.dimerco.hawb.aehawb.config;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
  
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  ModelMapper ModelMapper() {
    return new ModelMapper();
  }

  // @Bean
  // ObjectMapper objectMapper() {
  // return new ObjectMapper();
  // }

  // @Bean
  // RedisConnectionFactory redisConnectionFactory() {
  // return new LettuceConnectionFactory();
  // }

  // @Bean
  // RedisHelper redisHelper(RedisConnectionFactory factory,
  // ObjectMapper objectMapper) {
  // return new RedisHelper(factory, objectMapper);
  // }
}
