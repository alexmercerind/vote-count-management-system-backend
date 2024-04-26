package com.alexmercerind.votecountmanagementsystem.controller;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexmercerind.votecountmanagementsystem.dto.GenericResponseBody;
import com.alexmercerind.votecountmanagementsystem.dto.RoundFindAllResponseBodyItem;
import com.alexmercerind.votecountmanagementsystem.dto.RoundSaveRequestBody;
import com.alexmercerind.votecountmanagementsystem.service.RoundService;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/rounds")
public class RoundController {

    static private Logger logger = LoggerFactory.getLogger(RoundController.class);

    @Autowired
    private RoundService roundService;

    @GetMapping("/")
    public ResponseEntity<Iterable<RoundFindAllResponseBodyItem>> findAll()
            throws InterruptedException, ExecutionException {
        logger.info("RoundController::findAll");
        final Iterable<RoundFindAllResponseBodyItem> rounds = roundService.findAll();
        return ResponseEntity.ok(rounds);
    }

    @GetMapping("/delete/{roundId}/{roundDistrict}")
    public ResponseEntity<GenericResponseBody> deleteByRoundIdAndRoundDistrict(
            @PathVariable("roundId") Integer roundId,
            @PathVariable("roundDistrict") String roundDistrict) {
        logger.info("RoundController::deleteByRoundIdAndRoundDistrict");
        roundService.deleteByRoundIdAndRoundDistrict(roundId, roundDistrict);
        return ResponseEntity.ok(new GenericResponseBody(true, null));
    }

    @PostMapping("/save")
    public ResponseEntity<GenericResponseBody> save(@RequestBody RoundSaveRequestBody roundSaveRequestBody) {
        logger.info("RoundController::save");
        roundService.save(
                roundSaveRequestBody.getRoundId(),
                roundSaveRequestBody.getRoundDistrict(),
                roundSaveRequestBody.getCandidateVotes());
        return ResponseEntity.ok(new GenericResponseBody(true, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseBody> handleException(Exception e) {
        logger.info("RoundController::handleException");
        e.printStackTrace();
        return ResponseEntity.badRequest().body(new GenericResponseBody(false, e.getMessage()));
    }

}
