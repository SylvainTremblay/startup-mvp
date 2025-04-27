package com.csofdv.bmvpf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PredictionMetadataDto {

    private double inputTokens;
    private double inputCharacters;
    private double outputTokens;
    private double outputCharacters;
}
