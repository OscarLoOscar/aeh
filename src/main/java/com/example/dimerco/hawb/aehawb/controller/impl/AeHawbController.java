package com.example.dimerco.hawb.aehawb.controller.impl;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dimerco.hawb.aehawb.controller.AeHawbOperation;
import com.example.dimerco.hawb.aehawb.entity.BookingRequest;
import com.example.dimerco.hawb.aehawb.service.AeHawbService;


@RestController
@CrossOrigin(origins = "http://dimhkg.dimerco.com:8888",
    methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/V3NEWAPI/AE_HAWB")

public class AeHawbController implements AeHawbOperation {

  @Autowired
  private AeHawbService aeHawbService;

  @Autowired
  private Mapper mapper;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private RestTemplate restTemplate;



  @Override // Inherits @PostMapping from interface
  public BookingRequest parseBooking(@RequestBody String jsonResponse) {
    return aeHawbService.parseBookingRequest(jsonResponse);
  }

  // @Override // Inherits @GetMapping from interface
  // public BookmarkResponse bookmarkHawb(@PathVariable String id) {
  //   return aeHawbService.bookmarkHawb(id);
  // }

  @Override
  public String copyHawb(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'copyHawb'");
  }

  @Override
  public BookingRequest getDataById(String id, String currentUserId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getDataById'");
  }
}
//   @Override
//   public BookingRequest getDataById(@RequestParam String id,
//       @RequestParam(defaultValue = "Y9999") String currentUserId) {
//     return aeHawbService.getDataById(id, currentUserId);
//   }
// }
