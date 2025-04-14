package com.example.dimerco.hawb.aehawb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.dimerco.hawb.aehawb.entity.BookingRequest;

public interface AeHawbOperation {

  @PostMapping("/parse-booking") // amend later
  BookingRequest parseBooking(@RequestBody String jsonResponse);

  @GetMapping("/copyHAWB/{id}")
  String copyHawb(@PathVariable String id);

  @GetMapping("/GetDataById")
  BookingRequest getDataById(@RequestParam String id,
  @RequestParam(defaultValue = "Y9999") String currentUserId);

}
