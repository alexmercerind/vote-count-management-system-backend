package com.alexmercerind.votecountmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidate_image")
public class CandidateImage {
    @Id
    @Column(name = "candidate_id", nullable = false)
    private int candidateId;

    @Column(name = "candidate_image", nullable = false)
    private String candidateImage;
}
