package com.alexmercerind.votecountmanagementsystem.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRoundVoteCountId {
    private int candidateId;
    private int roundId;
    private String roundDistrict;
}
