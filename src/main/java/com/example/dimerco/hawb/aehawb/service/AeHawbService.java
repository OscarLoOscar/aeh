package com.example.dimerco.hawb.aehawb.service;

import com.example.dimerco.hawb.aehawb.model.BookingRequest;
import com.example.dimerco.hawb.aehawb.model.CombinedResult;
import com.example.dimerco.hawb.aehawb.response.BookmarkResponse;

public interface AeHawbService {
    BookingRequest parseBookingRequest(String jsonResponse);

    BookmarkResponse bookmarkHawb(String id);

    String bookmarkHawb(String destinationCode, CombinedResult data);
    //BookingRequest getDataById(String id, String currentUserId);
    String copyHawb(String id) ;

    BookingRequest getDataById(String id, String currentUserId);
}