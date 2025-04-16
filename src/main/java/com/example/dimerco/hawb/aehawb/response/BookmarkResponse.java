package com.example.dimerco.hawb.aehawb.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookmarkResponse {

    //add 
    private String houseId;
    
    private int bookmarkId;
    private String stationCode;
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

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class BookingPoData {
        private String poNo;
        private String poDate;
        private String invNo;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class HawbDim {
        private String dimension;
    }
}

