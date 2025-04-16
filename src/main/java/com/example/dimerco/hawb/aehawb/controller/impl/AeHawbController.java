package com.example.dimerco.hawb.aehawb.controller.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dimerco.hawb.aehawb.controller.AeHawbOperation;
import com.example.dimerco.hawb.aehawb.model.AehawbMapper;
import com.example.dimerco.hawb.aehawb.model.BookingRequest;
import com.example.dimerco.hawb.aehawb.model.CombinedResult;
import com.example.dimerco.hawb.aehawb.model.ErrorNotifier;
import com.example.dimerco.hawb.aehawb.model.FinalResult;
import com.example.dimerco.hawb.aehawb.response.BookmarkResponse;
import com.example.dimerco.hawb.aehawb.service.AeHawbService;

@RestController
@CrossOrigin(origins = "http://dimhkg.dimerco.com:8888", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/V3NEWAPI/AE_HAWB")

public class AeHawbController implements AeHawbOperation {

  @Autowired
  private ErrorNotifier errorNotifier;

  @Autowired
  private AeHawbService aeHawbService;

  @Autowired
  private AehawbMapper mapper;

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
  // return aeHawbService.bookmarkHawb(id);
  // }

  @Override
  public String copyHawb(String id) {
    try {
      return aeHawbService.copyHawb(id);
    } catch (Exception e) {
      errorNotifier.send("copyHawb", id, e.getMessage());
      throw new RuntimeException("Failed to copy HAWB with id: " + id, e);
    }
  }
  
  @Override
  public BookingRequest getDataById(String id, String currentUserId) {
    try {
      return aeHawbService.getDataById(id, currentUserId);
    } catch (Exception e) {
      errorNotifier.send("getDataById", String.format("id=%s, user=%s", id, currentUserId), e.getMessage());
      throw new RuntimeException("Failed to get data by ID", e);
    }
  }  

  @Override
  public ResponseEntity<FinalResult> testBookmarkFlow(@RequestBody CombinedResult input) {
    try {
      // Step 1: 從第一個 PO 推斷 destination
      String destinationCode = input.getInvoices()
          .get(0)
          .getPoNo()
          .substring(0, 2);

      // Step 2: bookmarkHawb → get houseId
      String houseId = aeHawbService.bookmarkHawb(destinationCode, input);

      // Step 3: Mock Instruction API 回傳
      // List<Instruction> mockInstructions = List.of(
      // new Instruction("ITM001", "Check customs"),
      // new Instruction("ITM002", "Prepare invoice copy")
      // );

      // Step 4: 組裝 FinalResult
      BookmarkResponse bookmark = BookmarkResponse.builder()
          .houseId(houseId)
          .build();

      FinalResult result = FinalResult.builder()
          .aiData(input)
          .bookmark(bookmark)
          // .instructions(mockInstructions)
          .build();

      return ResponseEntity.ok(result);
    } catch (Exception e) {
      errorNotifier.send("testBookmarkFlow", input.toString(), e.getMessage());
      throw new RuntimeException("Failed to complete bookmark + instruction flow", e);
    }
  }
}
// @Override
// public BookingRequest getDataById(@RequestParam String id,
// @RequestParam(defaultValue = "Y9999") String currentUserId) {
// return aeHawbService.getDataById(id, currentUserId);
// }
// }
