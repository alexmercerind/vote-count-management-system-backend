package com.alexmercerind.votecountmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexmercerind.votecountmanagementsystem.entity.Candidate;
import com.alexmercerind.votecountmanagementsystem.repository.CandidateRepository;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Iterable<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Candidate findById(int candidateId) {
        return candidateRepository.findById(candidateId).get();
    }

    public void deleteById(int candidateId) {
        candidateRepository.deleteById(candidateId);
    }

    public void save(Candidate candidate) {
        candidateRepository.save(candidate);
    }

}
