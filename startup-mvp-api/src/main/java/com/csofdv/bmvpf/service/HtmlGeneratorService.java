package com.csofdv.bmvpf.service;

import com.csofdv.bmvpf.dto.ApplicationDto;
import com.csofdv.bmvpf.exception.GenerationServiceException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class HtmlGenerationService extends GeneratorService {

    void generateApplicationCode(ApplicationDto applicationDto, String applicationFolder) throws GenerationServiceException {

    }
}
