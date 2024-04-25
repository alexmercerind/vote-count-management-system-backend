package com.alexmercerind.votecountmanagementsystem.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

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
import com.alexmercerind.votecountmanagementsystem.service.CandidateService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    static private Logger logger = LoggerFactory.getLogger(CandidateController.class);

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/")
    public ResponseEntity<Iterable<Candidate>> findAll() {
        logger.info("CandidateController::findAll");
        final Iterable<Candidate> candidates = candidateService.findAll();
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<Candidate> findByCandidateId(@PathVariable("candidateId") Integer candidateId) {
        logger.info("CandidateController::findByCandidateId");
        final Candidate candidate = candidateService.findByCandidateId(candidateId);
        return ResponseEntity.ok(candidate);
    }

    @GetMapping("/delete/{candidateId}")
    public ResponseEntity<GenericResponseBody> deleteByCandidateId(@PathVariable("candidateId") Integer candidateId) {
        logger.info("CandidateController::deleteByCandidateId");
        candidateService.deleteByCandidateId(candidateId);
        return ResponseEntity.ok(new GenericResponseBody(true, null));
    }

    @PostMapping("/save")
    public ResponseEntity<GenericResponseBody> save(@RequestBody CandidateSaveRequestBody candidateSaveRequestBody) {
        logger.info("CandidateController::save");
        candidateService.save(
                candidateSaveRequestBody.getCandidateName(),
                candidateSaveRequestBody.getCandidateParty(),
                candidateSaveRequestBody.getCandidateAddress(),
                candidateSaveRequestBody.getCandidateImage());
        return ResponseEntity.ok(new GenericResponseBody(true, null));
    }

    @GetMapping("/image/{candidateId}")
    @ResponseBody
    public void imageByCandidateId(
            @PathVariable("candidateId") Integer candidateId,
            HttpServletResponse httpServletResponse)
            throws IOException {
        logger.info("CandidateController::imageByCandidateId");
        candidateService.imageByCandidateId(candidateId, httpServletResponse);
    }

    @ExceptionHandler({
            IOException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<GenericResponseBody> handleIOExceptionAndNoSuchElementException(Exception e) {
        logger.info("CandidateController::handleIOExceptionAndNoSuchElementException");
        e.printStackTrace();
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseBody> handleException(Exception e) {
        logger.info("CandidateController::handleException");
        e.printStackTrace();
        return ResponseEntity.badRequest().body(new GenericResponseBody(false, e.getMessage()));
    }

}
