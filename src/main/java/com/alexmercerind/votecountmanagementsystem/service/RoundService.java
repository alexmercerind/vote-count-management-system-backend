package com.alexmercerind.votecountmanagementsystem.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexmercerind.votecountmanagementsystem.dto.CandidateVoteCount;
import com.alexmercerind.votecountmanagementsystem.dto.RoundFindAllResponseBodyItem;
import com.alexmercerind.votecountmanagementsystem.entity.Candidate;
import com.alexmercerind.votecountmanagementsystem.entity.CandidateRoundVoteCount;
import com.alexmercerind.votecountmanagementsystem.entity.Round;
import com.alexmercerind.votecountmanagementsystem.entity.id.CandidateRoundVoteCountId;
import com.alexmercerind.votecountmanagementsystem.entity.id.RoundId;
import com.alexmercerind.votecountmanagementsystem.repository.CandidateRepository;
import com.alexmercerind.votecountmanagementsystem.repository.CandidateRoundVoteCountRepository;
import com.alexmercerind.votecountmanagementsystem.repository.RoundRepository;

@Service
public class RoundService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private CandidateRoundVoteCountRepository candidateRoundVoteCountRepository;

    public List<RoundFindAllResponseBodyItem> findAll() {
        final ArrayList<RoundFindAllResponseBodyItem> roundFindAllResponseBodyItems = new ArrayList<RoundFindAllResponseBodyItem>();

        final Iterable<Round> rounds = roundRepository.findAll();
        final Iterable<Candidate> candidates = candidateRepository.findAll();

        for (final Round round : rounds) {
            final ArrayList<CandidateVoteCount> candidateVoteCounts = new ArrayList<CandidateVoteCount>();
            final RoundFindAllResponseBodyItem roundFindAllResponseBodyItem = new RoundFindAllResponseBodyItem();

            roundFindAllResponseBodyItem.setRoundId(round.getRoundId());
            roundFindAllResponseBodyItem.setRoundDistrict(round.getRoundDistrict());
            roundFindAllResponseBodyItem.setCandidateVoteCounts(candidateVoteCounts);

            for (final Candidate candidate : candidates) {
                final CandidateRoundVoteCountId candidateRoundVoteCountId = new CandidateRoundVoteCountId();
                candidateRoundVoteCountId.setCandidateId(candidate.getCandidateId());
                candidateRoundVoteCountId.setRoundId(round.getRoundId());
                candidateRoundVoteCountId.setRoundDistrict(round.getRoundDistrict());

                final CandidateRoundVoteCount candidateRoundVoteCount = candidateRoundVoteCountRepository
                        .findById(candidateRoundVoteCountId).get();
                candidateVoteCounts.add(new CandidateVoteCount(candidate, candidateRoundVoteCount.getVoteCount()));
            }
            roundFindAllResponseBodyItems.add(roundFindAllResponseBodyItem);
        }

        return roundFindAllResponseBodyItems;
    }

    public void deleteByRoundIdAndRoundDistrict(
            int roundId,
            @NonNull String roundDistrict) {
        roundRepository.deleteById(new RoundId(roundId, roundDistrict));
    }

    @Transactional()
    public void save(
            int roundId,
            @NonNull String roundDistrict,
            @NonNull HashMap<Integer, BigInteger> candidateVotes) throws IllegalArgumentException {

        final List<Integer> candidateIdsCurrent = candidateRepository
                .findAll()
                .stream()
                .map(Candidate::getCandidateId)
                .sorted()
                .toList();
        final List<Integer> candidateIdsNew = candidateVotes
                .keySet()
                .stream()
                .sorted()
                .toList();

        for (final Integer candidateId : candidateIdsCurrent) {
            if (!candidateIdsNew.contains(candidateId)) {
                throw new IllegalArgumentException(
                        String.format("Vote count not provided for Candidate ID: %d", candidateId));
            }
        }
        for (final Integer candidateId : candidateIdsNew) {
            if (!candidateIdsCurrent.contains(candidateId)) {
                throw new IllegalArgumentException(
                        String.format("Vote count provided for inexistent Candidate ID: %d", candidateId));
            }
        }
        for (int i = 0; i < Math.min(candidateIdsCurrent.size(), candidateIdsNew.size()); i++) {
            if (!candidateIdsCurrent.get(i).equals(candidateIdsNew.get(i))) {
                throw new IllegalArgumentException(
                        String.format(
                                "Candidate ID mismatch: %d != %d",
                                candidateIdsCurrent.get(i),
                                candidateIdsNew.get(i)));
            }
        }

        roundRepository.deleteById(new RoundId(roundId, roundDistrict));

        final Round round = new Round();
        round.setRoundId(roundId);
        round.setRoundDistrict(roundDistrict);
        roundRepository.save(round);

        candidateVotes
                .entrySet()
                .forEach(
                        entry -> {
                            final Integer candidateId = entry.getKey();
                            final BigInteger voteCount = entry.getValue();

                            final CandidateRoundVoteCount candidateRoundVoteCount = new CandidateRoundVoteCount();
                            candidateRoundVoteCount.setCandidateId(candidateId);
                            candidateRoundVoteCount.setRoundId(roundId);
                            candidateRoundVoteCount.setRoundDistrict(roundDistrict);
                            candidateRoundVoteCount.setVoteCount(voteCount);
                            candidateRoundVoteCountRepository.save(candidateRoundVoteCount);

                        });
    }
}
