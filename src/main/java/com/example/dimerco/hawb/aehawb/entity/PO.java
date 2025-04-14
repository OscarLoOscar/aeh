package com.example.dimerco.hawb.aehawb.entity;


import com.google.auto.value.AutoValue.Builder;
// import jakarta.persistence.Column;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

// @jakarta.persistence.Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PO {
  private Long houseId;
  private List<POData> poData;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public class POData {
    private String itemNo;
    private String soNo;
    private String invNo;
    private String plNo;
    private LocalDate invDate;
    private Double invAmount;
    private String outerPacks;
    private String innerPacks;
    private String qty;
    private String qtyUom;
    private String gwt;
    private String gwtUom;
    private String currency;
    private String finalDestination;
    private String extra1;
    private String extra2;
    private String extra3;
    private String extra4;
    private String extra5;
    private String extra6;
    private String remark;
    private String description;
    private String container;
  }
}
