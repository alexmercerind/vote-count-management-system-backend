package com.alexmercerind.votecountmanagementsystem.entity;

import java.math.BigInteger;

import com.alexmercerind.votecountmanagementsystem.entity.id.CandidateRoundVoteCountId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CandidateRoundVoteCountId.class)
@Table(name = "candidate_round_vote_count")
public class CandidateRoundVoteCount {
    @Id
    @Column(name = "candidate_id", nullable = false)
    private int candidateId;

    @Id
    @Column(name = "round_id", nullable = false)
    private int roundId;

    @Id
    @Column(name = "round_district", nullable = false)
    private String roundDistrict;

    @Id
    @Column(name = "round_constituency", nullable = false)
    private String roundConstituency;

    @Column(name = "vote_count", nullable = false)
    private BigInteger voteCount;
}
