package com.example.dimerco.hawb.aehawb.service;

import com.example.dimerco.hawb.aehawb.config.DestinationConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DestinationResolverService {

  private final Map<String, String> destinationMap;

  public DestinationResolverService(DestinationConfig config) {
    this.destinationMap = config.getDestinations();
  }

  public String resolveBookmarkId(String prefix) {
    String key = prefix.toUpperCase();
    if (!destinationMap.containsKey(key)) {
      throw new IllegalArgumentException("Unsupported destination prefix: " + key);
    }
    return destinationMap.get(key);
  }

  public boolean isSupported(String prefix) {
    return destinationMap.containsKey(prefix.toUpperCase());
  }
}
