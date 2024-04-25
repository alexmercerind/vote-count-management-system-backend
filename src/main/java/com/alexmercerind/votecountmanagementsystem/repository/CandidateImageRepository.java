package com.alexmercerind.votecountmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexmercerind.votecountmanagementsystem.entity.CandidateImage;

public interface CandidateImageRepository extends JpaRepository<CandidateImage, Integer> {

}
