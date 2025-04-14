package com.example.dimerco.hawb.aehawb.service;

import com.example.dimerco.hawb.aehawb.entity.BookingRequest;
import com.example.dimerco.hawb.aehawb.response.BookmarkResponse;

public interface AeHawbService {
    BookingRequest parseBookingRequest(String jsonResponse);

    BookmarkResponse bookmarkHawb(String id);

    //BookingRequest getDataById(String id, String currentUserId);
}