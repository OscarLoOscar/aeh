package com.example.dimerco.hawb.aehawb;

import com.example.dimerco.hawb.aehawb.model.InputData;
import com.example.dimerco.hawb.aehawb.model.InvoiceData;
import com.example.dimerco.hawb.aehawb.model.CombinedResult;
import com.example.dimerco.hawb.aehawb.model.Mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class MapperTest {

  private final Mapper mapper = new Mapper();
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void testMapToCombinedResult() throws Exception {
    List<InputData> mockDimensions = List.of(
        InputData.builder()//
            .PCS(1)//
            .DIMUOM("CTN")//
            .gwt(160.0)//
            .wtUom("KGS")//
            .length(122.0)//
            .width(100.0)//
            .height(147.0)//
            .build()//
    );

    List<InvoiceData> mockInvoices = List.of(
        InvoiceData.builder()//
            .poNo("41327168")//
            .invDate("03/18/2025")//
            .currency("USD")//
            .invAmount("369.50")//
            .build()//
    );

    CombinedResult result = mapper.mapToCombinedResult(mockDimensions, mockInvoices);
    String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
    System.out.println("=== CombinedResult JSON ===");
    System.out.println(json);
    log.info(json);
  }
}
