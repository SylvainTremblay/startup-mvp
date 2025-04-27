package com.csofdv.bmvpf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PredictionResponseDto {

    private PredictionMetadataDto predictionMetadataDto;

    private List<PredictionDto> predictionList;
}
