package com.example.dimerco.hawb.aehawb.entity;

import java.time.LocalDateTime;
import java.util.List;
// import org.springframework.data.annotation.Id;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonIgnoreProperties(ignoreUnknown = true) 
// @Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
  private String stationCode;
  private int customerId;
  private int shipperId;
  private int cneeId;
  private String placeOfReceiptCode;
  private String portOfDeptCode;
  private String portOfDstnCode;
  private String placeOfDeliveryCode;
  private String airPortOfDeptCode;
  private String airPortOfDstnCode;
  private String thirdParty;
  private boolean needThirdPartyAfterConfirm;
  private String finalImport;
  private String serviceLevel;
  private String handlingCode;
  private String tradeTerm;
  private String move;
  private String frt;
  private String other;
  private String availDate;
  private boolean needBooking;
  private boolean bookingConfirm;
  private boolean needPoms;
  private boolean needDimension;
  private boolean needPickup;
  private boolean needInstruction;
  private boolean needMilestone;
  private String remark;
  private String mark;
  private String desc;
  private String salesPerson;
  private boolean appHawbNoContinue;
  private String pcs;
  private String slac;
  private String gwt;
  private String cwt;
  private String vwt;
  private BookingExtra bookingExtra;
  private List<BookingPoData> bookingPoDatas;
  private List<HawbDim> hawbDims;

  // @Entity
  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class BookingExtra {
    private String extra0;
    private String extra1;
    private String extra2;
    private String extra3;
    private String extra4;
    private String extra5;
    private String extra6;
    private String extra7;
    private String extra8;
    private String extra9;
  }

  // @Entity
  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class BookingPoData {
    private String poNo;
    private String poDate;
    private String invNo;
  }

  // @Entity
  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class HawbDim {
    private String dimension;
  }
}
