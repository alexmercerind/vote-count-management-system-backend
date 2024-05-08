package com.alexmercerind.votecountmanagementsystem.dto;

import java.math.BigInteger;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoundSaveRequestBody {
    private int roundId;
    private String roundDistrict;
    private String roundConstituency;
    private HashMap<Integer, BigInteger> candidateVotes;
}
