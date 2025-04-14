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
public class InputData {
  private int PCS;
  private String DIMUOM;
  private double gwt;
  private String wtUom;
  private double length;
  private double width;
  private double height;
}
