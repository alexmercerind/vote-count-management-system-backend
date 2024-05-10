package com.alexmercerind.votecountmanagementsystem.entity;

import com.alexmercerind.votecountmanagementsystem.entity.id.RoundId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(RoundId.class)
@Table(name = "round")
public class Round {
    @Id
    @Column(name = "round_id", nullable = false)
    private int roundId;

    @Id
    @Column(name = "round_district", nullable = false)
    private String roundDistrict;

    @Id
    @Column(name ="round_constituency", nullable = false)
    private String roundConstituency;
}
