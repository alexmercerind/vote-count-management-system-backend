package com.alexmercerind.votecountmanagementsystem.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.alexmercerind.votecountmanagementsystem.dto.CandidateSaveRequestBody;
import com.alexmercerind.votecountmanagementsystem.dto.GenericResponseBody;
import com.alexmercerind.votecountmanagementsystem.entity.Candidate;
import com.alexmercerind.votecountmanagementsystem.service.CandidateService;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    static private Logger logger = LoggerFactory.getLogger(CandidateController.class);

    @Autowired
    private CandidateService candidateService;

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
        return ResponseEntity.ok(new GenericResponseBody(true, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseBody> handleException(Exception e) {
        logger.info("CandidateController::handleException");
        logger.error(e.toString());
        return ResponseEntity.badRequest().body(new GenericResponseBody(false, e.getMessage()));
    }

}
