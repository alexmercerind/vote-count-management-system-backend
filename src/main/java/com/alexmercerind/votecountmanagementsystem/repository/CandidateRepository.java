package com.alexmercerind.votecountmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexmercerind.votecountmanagementsystem.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

}
