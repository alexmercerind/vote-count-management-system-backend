package com.alexmercerind.votecountmanagementsystem.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alexmercerind.votecountmanagementsystem.dto.CandidateSaveRequestBody;
import com.alexmercerind.votecountmanagementsystem.dto.GenericResponseBody;
import com.alexmercerind.votecountmanagementsystem.entity.Candidate;
import com.alexmercerind.votecountmanagementsystem.entity.CandidateImage;
import com.alexmercerind.votecountmanagementsystem.service.CandidateImageService;
import com.alexmercerind.votecountmanagementsystem.service.CandidateService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    static private Logger logger = LoggerFactory.getLogger(CandidateController.class);

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateImageService candidateImageService;

    @GetMapping("/")
    public ResponseEntity<Iterable<Candidate>> findAll() {
        logger.info("CandidateController::findAll");
        return ResponseEntity.ok(candidateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> findById(@PathVariable("id") Integer id) {
        logger.info("CandidateController::findById");
        return ResponseEntity.ok(candidateService.findById(id));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<GenericResponseBody> deleteById(@PathVariable("id") Integer id) {
        logger.info("CandidateController::deleteById");

        candidateService.deleteById(id);

        return ResponseEntity.ok(new GenericResponseBody(true, null));
    }

    @PostMapping("/save")
    public ResponseEntity<GenericResponseBody> save(@RequestBody CandidateSaveRequestBody candidateSaveRequestBody) {
        logger.info("CandidateController::save");

        final Candidate candidate = new Candidate();
        candidate.setCandidateName(candidateSaveRequestBody.getCandidateName());
        candidate.setCandidateParty(candidateSaveRequestBody.getCandidateParty());
        candidate.setCandidateAddress(candidateSaveRequestBody.getCandidateAddress());
        candidateService.save(candidate);

        if (candidateSaveRequestBody.getCandidateImage() != null) {
            final CandidateImage candidateImage = new CandidateImage();
            candidateImage.setCandidateId(candidate.getCandidateId());
            candidateImage.setCandidateImage(candidateSaveRequestBody.getCandidateImage());
            candidateImageService.save(candidateImage);
        }

        return ResponseEntity.ok(new GenericResponseBody(true, null));
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public void imageById(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        logger.info("CandidateController::imageById");

        final CandidateImage candidateImage = candidateImageService.findById(id);

        final String[] parts = candidateImage.getCandidateImage().split(",");

        final String mime = parts[0].split(":")[1].split(";")[0];
        final byte[] image = Base64.getDecoder().decode(parts[1].getBytes(StandardCharsets.UTF_8));

        response.setContentType(mime);
        response.setContentLength(image.length);
        response.getOutputStream().write(image);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseBody> handleException(Exception e) {
        logger.info("CandidateController::handleException");
        logger.error(e.toString());
        return ResponseEntity.badRequest().body(new GenericResponseBody(false, e.getMessage()));
    }

}
