package com.example.dimerco.hawb.aehawb.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkRequest {
  private String houseId;
  private List<InvoiceData> bookingPoDatas;
  private List<HawbDimInput> hawbDims;
}