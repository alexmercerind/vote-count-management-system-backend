package com.alexmercerind.votecountmanagementsystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoundFindAllResponseBodyItem {
    private int roundId;
    private String roundDistrict;
    private String roundConstituency;
    private List<CandidateVoteCount> candidateVoteCounts;
}
