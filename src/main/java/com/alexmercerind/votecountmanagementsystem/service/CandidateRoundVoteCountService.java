package com.alexmercerind.votecountmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexmercerind.votecountmanagementsystem.entity.CandidateRoundVoteCount;
import com.alexmercerind.votecountmanagementsystem.entity.id.CandidateRoundVoteCountId;
import com.alexmercerind.votecountmanagementsystem.repository.CandidateRoundVoteCountRepository;

@Service
public class CandidateRoundVoteCountService {

    @Autowired
    private CandidateRoundVoteCountRepository candidateRoundVoteCountRepository;

    public Iterable<CandidateRoundVoteCount> findAll() {
        return candidateRoundVoteCountRepository.findAll();
    }

    public CandidateRoundVoteCount findById(CandidateRoundVoteCountId candidateRoundVoteCountId) {
        return candidateRoundVoteCountRepository.findById(candidateRoundVoteCountId).get();
    }

    public void deleteById(CandidateRoundVoteCountId candidateRoundVoteCountId) {
        candidateRoundVoteCountRepository.deleteById(candidateRoundVoteCountId);
    }

    public void save(CandidateRoundVoteCount candidateRoundVoteCount) {
        candidateRoundVoteCountRepository.save(candidateRoundVoteCount);
    }
}
