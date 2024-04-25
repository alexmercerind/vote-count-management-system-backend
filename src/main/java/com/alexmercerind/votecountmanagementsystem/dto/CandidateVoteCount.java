package com.alexmercerind.votecountmanagementsystem.dto;

import java.math.BigInteger;

import com.alexmercerind.votecountmanagementsystem.entity.Candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateVoteCount {
    private Candidate candidate;
    private BigInteger voteCount;
}
