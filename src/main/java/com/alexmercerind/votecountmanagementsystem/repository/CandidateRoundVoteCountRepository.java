package com.alexmercerind.votecountmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexmercerind.votecountmanagementsystem.entity.CandidateRoundVoteCount;
import com.alexmercerind.votecountmanagementsystem.entity.id.CandidateRoundVoteCountId;

public interface CandidateRoundVoteCountRepository
        extends JpaRepository<CandidateRoundVoteCount, CandidateRoundVoteCountId> {

}
