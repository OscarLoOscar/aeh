package com.example.dimerco.hawb.aehawb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dimerco.hawb.aehawb.model.BookingRequest;
import com.example.dimerco.hawb.aehawb.model.BookmarkRequest;
import com.example.dimerco.hawb.aehawb.model.CombinedResult;
import com.example.dimerco.hawb.aehawb.model.ErrorNotifier;
import com.example.dimerco.hawb.aehawb.model.HawbDimInput;
import com.example.dimerco.hawb.aehawb.model.InvoiceData;
import com.example.dimerco.hawb.aehawb.response.BookmarkResponse;
import com.example.dimerco.hawb.aehawb.service.AeHawbService;
import com.example.dimerco.hawb.aehawb.service.DestinationResolverService;
import com.fasterxml.jackson.databind.ObjectMapper;

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

  @Value("${api.hawb.bookmark_endpoint}")
  private String bookmarkEndpoint;

  @Autowired
  private DestinationResolverService destinationResolverService;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private AccessTokenProviderImpl accessTokenProvider;

  @Autowired
  private ErrorNotifier errorNotifier;

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
  // String url = buildUrl(basePath, dataEndpoint)
  // + String.format("?id=%s&currentUserId=%s", id, currentUserId);
  // return restTemplate.getForObject(url, BookingRequest.class);
  // }

  // Helper method to build URLs
  private String buildUrl(String... paths) {
    return domain + String.join("/", paths);
  }

  @Override
  public String bookmarkHawb(String destinationCode, CombinedResult data) {
    try {
      String bookmarkId = destinationResolverService.resolveBookmarkId(destinationCode);

      BookmarkRequest request = convertToBookmarkRequest(data); // 假設你有這個方法

      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessTokenProvider.getAccessToken());
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<BookmarkRequest> entity = new HttpEntity<>(request, headers);

      String url = domain + bookmarkEndpoint.replace("{bookmarkId}", bookmarkId);

      ResponseEntity<BookmarkResponse> response = restTemplate.exchange(
          url, HttpMethod.POST, entity, BookmarkResponse.class);

      return response.getBody().getHouseId();
    } catch (Exception e) {
      errorNotifier.send("bookmarkHawb", destinationCode, e.getMessage());
      throw new RuntimeException("Bookmark API failed", e);
    }
  }

  private BookmarkRequest convertToBookmarkRequest(CombinedResult data) {
    List<InvoiceData> invoiceList = data.getInvoices();

    List<HawbDimInput> dimList = data.getDimensions().stream()
        .map(d -> {
          String dimStr = String.format("%.0f*%.0f*%.0f*%d", //
              d.getLength(), //
              d.getWidth(), //
              d.getHeight(), //
              d.getPCS());//
          return new HawbDimInput(dimStr);
        })
        .toList();

    return BookmarkRequest.builder()
        .bookingPoDatas(invoiceList)
        .hawbDims(dimList)
        .build();
  }

  @Override
public String copyHawb(String id) {
  try {
    String url = buildUrl(basePath, copyEndpoint, id);
    return restTemplate.getForObject(url, String.class);
  } catch (Exception e) {
    errorNotifier.send("copyHawb", id, e.getMessage());
    throw new RuntimeException("Copy HAWB failed", e);
  }
}

@Override
public BookingRequest getDataById(String id, String currentUserId) {
  try {
    String url = buildUrl(basePath, dataEndpoint) +
                 String.format("?id=%s&currentUserId=%s", id, currentUserId);

    return restTemplate.getForObject(url, BookingRequest.class);
  } catch (Exception e) {
    errorNotifier.send("getDataById", id + ", " + currentUserId, e.getMessage());
    throw new RuntimeException("GetDataById API failed", e);
  }
}

}