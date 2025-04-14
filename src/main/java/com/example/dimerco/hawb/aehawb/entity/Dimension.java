package com.example.dimerco.hawb.aehawb.entity;

import java.util.List;
import com.google.auto.value.AutoValue.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class Dimension {
  private Long houseId;
  private List<DimensionDTO> bookingDims; // List of dimension DTOs

  @Setter
  @Getter
  @AllArgsConstructor
  @Builder
  public static class DimensionDTO {
    private String dimension;
  }
}
