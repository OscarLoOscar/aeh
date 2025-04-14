package com.example.dimerco.hawb.aehawb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceData {
  private String poNo;
  private String invDate;
  private String currency;
  private String invAmount; 
}