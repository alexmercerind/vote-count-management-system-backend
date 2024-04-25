package com.alexmercerind.votecountmanagementsystem.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.alexmercerind.votecountmanagementsystem.entity.Candidate;
import com.alexmercerind.votecountmanagementsystem.entity.CandidateImage;
import com.alexmercerind.votecountmanagementsystem.repository.CandidateImageRepository;
import com.alexmercerind.votecountmanagementsystem.repository.CandidateRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateImageRepository candidateImageRepository;

    public Iterable<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Candidate findByCandidateId(int candidateId) {
        return candidateRepository.findById(candidateId).get();
    }

    public void deleteByCandidateId(int candidateId) {
        candidateRepository.deleteById(candidateId);
    }

    public void save(
            @NonNull String candidateName,
            @NonNull String candidateParty,
            @NonNull String candidateAddress,
            @Nullable String candidateImage) {

        final Candidate candidate = new Candidate();
        candidate.setCandidateName(candidateName);
        candidate.setCandidateParty(candidateParty);
        candidate.setCandidateAddress(candidateAddress);
        candidateRepository.save(candidate);

        if (candidateImage != null) {

            final CandidateImage candidateImage_ = new CandidateImage();
            candidateImage_.setCandidateId(candidate.getCandidateId());
            candidateImage_.setCandidateImage(candidateImage);
            candidateImageRepository.save(candidateImage_);

        }
    }

    public void imageByCandidateId(int candidateId, HttpServletResponse httpServletResponse) throws IOException {

        final CandidateImage candidateImage = candidateImageRepository.findById(candidateId).get();

        final String[] parts = candidateImage.getCandidateImage().split(",");

        final String mime = parts[0].split(":")[1].split(";")[0];
        final byte[] image = Base64.getDecoder().decode(parts[1].getBytes(StandardCharsets.UTF_8));

        httpServletResponse.setContentType(mime);
        httpServletResponse.setContentLength(image.length);
        httpServletResponse.getOutputStream().write(image);

    }

}
