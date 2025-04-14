package com.example.dimerco.hawb.aehawb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.dimerco.hawb.aehawb.entity.BookingRequest;
import com.example.dimerco.hawb.aehawb.response.BookmarkResponse;
import com.example.dimerco.hawb.aehawb.service.AeHawbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;

@Service
public class AeHawbServiceImpl implements AeHawbService {

  @Value("${api.hawb.domain}")
  private String domain;

  @Value("${api.hawb.base_path}")
  private String basePath;

  @Value("${api.hawb.copy_endpoint}")
  private String copyEndpoint;

  @Value("${api.hawb.data_endpoint}")
  private String dataEndpoint;

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private ObjectMapper objectMapper;



  @Override
  public BookingRequest parseBookingRequest(String jsonResponse) {
    try {
      return objectMapper.readValue(jsonResponse, BookingRequest.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse booking request", e);
    }
  }

  @Override
  public BookmarkResponse bookmarkHawb(String id) {
    String url = buildUrl(basePath, copyEndpoint, id);
    return restTemplate.getForObject(url, BookmarkResponse.class);
  }

  // @Override
  // public BookingRequest getDataById(String id, String currentUserId) {
  //   String url = buildUrl(basePath, dataEndpoint)
  //       + String.format("?id=%s&currentUserId=%s", id, currentUserId);
  //   return restTemplate.getForObject(url, BookingRequest.class);
  // }

  // Helper method to build URLs
  private String buildUrl(String... paths) {
    return domain + String.join("/", paths);
  }
}
