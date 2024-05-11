package com.alexmercerind.votecountmanagementsystem.controller;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexmercerind.votecountmanagementsystem.configuration.SecurityConfiguration;
import com.alexmercerind.votecountmanagementsystem.dto.GenericResponseBody;
import com.alexmercerind.votecountmanagementsystem.dto.RoundFindAllOrderBy;
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
    public ResponseEntity<Iterable<RoundFindAllResponseBodyItem>> findAll(
            @RequestParam(value = "orderBy", defaultValue = "candidateId") RoundFindAllOrderBy orderBy,
            @RequestParam(value = "asc", defaultValue = "true") boolean asc)
            throws InterruptedException, ExecutionException {
        logger.info("RoundController::findAll");
        final Iterable<RoundFindAllResponseBodyItem> rounds = roundService.findAll(orderBy, asc);
        return ResponseEntity.ok(rounds);
    }

    @GetMapping("/delete/{roundId}/{roundDistrict}/{roundConstituency}")
    public ResponseEntity<GenericResponseBody> deleteByRoundIdAndRoundDistrictAndRoundConstituency(
            @PathVariable("roundId") Integer roundId,
            @PathVariable("roundDistrict") String roundDistrict,
            @PathVariable("roundConstituency") String roundConstituency,
            @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("RoundController::deleteByRoundIdAndRoundDistrictAndRoundConstituency");
        logger.info(userDetails.getAuthorities().toString());

        if (!(userDetails.getUsername().equals("admin")
                || (userDetails.getUsername().equals(roundConstituency.toLowerCase().replaceAll(" ", ""))))) {
            throw new SecurityException("Unauthorized");
        }

        roundService.deleteByRoundIdAndRoundDistrictAndRoundConstituency(roundId, roundDistrict, roundConstituency);
        return ResponseEntity.ok(new GenericResponseBody(true, null));
    }

    @PostMapping("/save")
    public ResponseEntity<GenericResponseBody> save(@RequestBody RoundSaveRequestBody roundSaveRequestBody,
            @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("RoundController::save");
        logger.info(userDetails.getAuthorities().toString());

        if (!(userDetails.getUsername().equals("admin")
                || (userDetails.getUsername()
                        .equals(roundSaveRequestBody.getRoundConstituency().toLowerCase().replaceAll(" ", ""))))) {
            throw new SecurityException("Unauthorized");
        }

        roundService.save(
                roundSaveRequestBody.getRoundId(),
                roundSaveRequestBody.getRoundDistrict(),
                roundSaveRequestBody.getRoundConstituency(),
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
