package com.alexmercerind.votecountmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateSaveRequestBody {
    private String candidateName;
    private String candidateParty;
    private String candidateAddress;
    private String candidateImage;
}
