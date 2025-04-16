package com.example.dimerco.hawb.aehawb.model;

import java.util.List;

import com.example.dimerco.hawb.aehawb.response.BookmarkResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalResult {
    private CombinedResult aiData;
    private BookmarkResponse bookmark;
    private List<Instruction> instructions;
}