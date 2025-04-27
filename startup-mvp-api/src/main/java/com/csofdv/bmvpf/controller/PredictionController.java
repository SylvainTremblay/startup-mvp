package com.csofdv.bmvpf.controller;

import com.csofdv.bmvpf.dto.PredictionRequestDto;
import com.csofdv.bmvpf.dto.PredictionResponseDto;
import com.csofdv.bmvpf.service.PredictionException;
import com.csofdv.bmvpf.service.PredictionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
class PredictionController {

    private final PredictionService predictionService;

    PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @RequestMapping(value = "/generateCode", method = RequestMethod.POST)
    @ResponseBody
    public PredictionResponseDto generateCode(@RequestBody PredictionRequestDto request) {
        String parameters = "{\"temperature\": 0.5,\"maxOutputTokens\": 256}";
        try {
            return predictionService.generateCode(request.getPrompt(), parameters);
        } catch (PredictionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
