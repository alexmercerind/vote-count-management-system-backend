package com.alexmercerind.votecountmanagementsystem.repository;

import com.alexmercerind.votecountmanagementsystem.entity.Round;
import com.alexmercerind.votecountmanagementsystem.entity.id.RoundId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, RoundId> {

}
