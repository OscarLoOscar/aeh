
package com.example.dimerco.hawb.aehawb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.example.dimerco.hawb.aehawb.entity.BookingRequest;
import com.example.dimerco.hawb.aehawb.model.AehawbMapper;
import com.example.dimerco.hawb.aehawb.model.ErrorNotifier;
import com.example.dimerco.hawb.aehawb.service.AeHawbService;

@SpringBootTest
@AutoConfigureMockMvc
public class AeHawbControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AeHawbService aeHawbService;

  @MockBean
  private ErrorNotifier errorNotifier;

  @MockBean
  private AehawbMapper aehawbMapper;

  @MockBean
  private RestTemplate restTemplate;

  @MockBean
  private ModelMapper modelMapper;

  @Test
  void testCopyHawb() throws Exception {
    Mockito.when(aeHawbService.copyHawb("123"))//
        .thenReturn("COPY_OK");

    mockMvc.perform(get("/V3NEWAPI/AE_HAWB/copyHAWB/123"))//
        .andExpect(status().isOk())//
        .andExpect(content().string("COPY_OK"));
  }

  @Test
  void testGetDataByID() throws Exception {
    BookingRequest mockRequest = new BookingRequest();
    Mockito.when(aeHawbService.getDataById("ABC", "U001"))//
        .thenReturn(mockRequest);

    mockMvc.perform(get("/V3NEWAPI/AE_HAWB/GetDataById")//
        .param("id", "ABC")//
        .param("currentUserId", "U001"))//
        .andExpect(status().isOk());
  }
}