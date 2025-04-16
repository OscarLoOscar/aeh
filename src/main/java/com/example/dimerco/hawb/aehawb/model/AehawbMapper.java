package com.example.dimerco.hawb.aehawb.model;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.example.dimerco.hawb.aehawb.entity.BookingRequest;
import com.example.dimerco.hawb.aehawb.entity.BookingRequest.BookingPoData;
import com.example.dimerco.hawb.aehawb.entity.BookingRequest.HawbDim;

@Component
public class AehawbMapper {
  public BookingRequest mapInputToBookingRequest(List<InputData> inputData) {
    // Map the input data to the BookingRequest fields
    BookingRequest.BookingExtra bookingExtra = BookingRequest.BookingExtra.builder()
        .extra0("Default extra") // You can modify it based on your input if required
        .build();

    // Create BookingPoData (You might want to adjust this based on the actual input
    // for PO)
    List<BookingPoData> bookingPoDataList = inputData.stream()
        .map(input -> BookingPoData.builder()
            .poNo("PO_" + input.getPCS()) // Example, adjust as needed
            .poDate("2025-04-13") // You can extract or provide the actual date
            .invNo("INV_" + input.getPCS()) // Example, adjust as needed
            .build())
        .collect(Collectors.toList());

    // Create HawbDim list based on input data
    List<HawbDim> hawbDims = inputData.stream().map(input -> HawbDim
        .builder()//
        .dimension(input.getLength() + "x"
            + input.getWidth() + "x"
            + input.getHeight())
        .build())//
        .collect(Collectors.toList());

    // Map all values from input to BookingRequest
    return BookingRequest.builder().stationCode("StationCode") // Provide a default or dynamic value
        .customerId(123) // Provide a default or dynamic value
        .shipperId(456) // Provide a default or dynamic value
        .cneeId(789) // Provide a default or dynamic value
        .placeOfReceiptCode("PORC") // Provide a default or dynamic value
        .portOfDeptCode("PODC") // Provide a default or dynamic value
        .portOfDstnCode("PODST") // Provide a default or dynamic value
        .placeOfDeliveryCode("POD") // Provide a default or dynamic value
        .airPortOfDeptCode("APDC") // Provide a default or dynamic value
        .airPortOfDstnCode("APDST") // Provide a default or dynamic value
        .thirdParty("ThirdParty") // Provide a default or dynamic value
        .needThirdPartyAfterConfirm(true)
        .finalImport("FinalImport") // Provide a default or dynamic value
        .serviceLevel("Standard") // Provide a default or dynamic value
        .handlingCode("HandlingCode") // Provide a default or dynamic value
        .tradeTerm("TradeTerm") // Provide a default or dynamic value
        .move("Move") // Provide a default or dynamic value
        .frt("FRT") // Provide a default or dynamic value
        .other("Other") // Provide a default or dynamic value
        .availDate("2025-04-13") // Provide a default or dynamic value
        .needBooking(true) // Example: You can map this from input if applicable
        .bookingConfirm(true) // Example: You can map this from input if applicable
        .needPoms(true) // Example: You can map this from input if applicable
        .needDimension(true).needPickup(true) // Example: You can map this from input if
                                              // applicable
        .needInstruction(true) // Example: You can map this from input if applicable
        .needMilestone(true) // Example: You can map this from input if applicable
        .remark("Remark") // Provide a default or dynamic value
        .mark("Mark") // Provide a default or dynamic value
        .desc("Description") // Provide a default or dynamic value
        .salesPerson("SalesPerson") // Provide a default or dynamic value
        .appHawbNoContinue(true) // Example: You can map this from input if applicable
        .pcs("1") // Map from PCS in input
        .slac("SLAC") // Provide a default or dynamic value
        .gwt(String.valueOf(inputData.get(0).getGwt())) // Map GWT from input
        .cwt("CWT") // Provide a default or dynamic value
        .vwt("VWT") // Provide a default or dynamic value
        .bookingExtra(bookingExtra)//
        .bookingPoDatas(bookingPoDataList)//
        .hawbDims(hawbDims)//
        .build();
  }

  public CombinedResult mapToCombinedResult(List<InputData> inputDataList, List<InvoiceData> invoiceList) {
    return CombinedResult.builder()
        .dimensions(inputDataList)
        .invoices(invoiceList)
        .build();
  }
}
