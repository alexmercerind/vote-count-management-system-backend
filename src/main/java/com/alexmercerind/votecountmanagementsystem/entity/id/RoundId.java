package com.alexmercerind.votecountmanagementsystem.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundId {
    private int roundId;
    private String roundDistrict;
    private String roundConstituency;
}
