package com.alexmercerind.votecountmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexmercerind.votecountmanagementsystem.entity.CandidateImage;
import com.alexmercerind.votecountmanagementsystem.repository.CandidateImageRepository;

@Service
public class CandidateImageService {

    @Autowired
    private CandidateImageRepository candidateImageRepository;

    public CandidateImage findById(int candidateId) {
        return candidateImageRepository.findById(candidateId).get();
    }

    public void deleteById(int candidateId) {
        candidateImageRepository.deleteById(candidateId);
    }

    public void save(CandidateImage image) {
        candidateImageRepository.save(image);
    }
}
