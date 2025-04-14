package com.example.dimerco.hawb.aehawb.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class Instruction {
  private Long houseId;
  private List<InstructionDTO> bookingInstruction; // List of instructions

  @Setter
  @Getter
  @AllArgsConstructor
  @Builder
  public static class InstructionDTO {
    private String category = "Operator";
    private String subject = "Bill KS";
  }
}

