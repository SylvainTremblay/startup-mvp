package com.csofdv.bmvpf.service;

import com.csofdv.bmvpf.dto.ApplicationDto;
import com.csofdv.bmvpf.dto.ViewDto;
import com.csofdv.bmvpf.exception.GenerationServiceException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Log
public class HtmlGeneratorService extends GeneratorService {

    public static final String HTML_VIEW_TEMPLATE = "html/view.ftl";

    void generateApplicationCode(ApplicationDto applicationDto, String applicationFolder) throws GenerationServiceException {
        log.entering(HtmlGeneratorService.class.getSimpleName(),"generateApplicationCode", applicationDto.getApplicationName());
        String htmlFolder = applicationFolder + "/" + applicationDto.getApplicationName();
        if (!new File(htmlFolder).mkdirs()) {
            log.info("HTML folder already exists: " + htmlFolder);
        } else {
            log.info("HTML folder created: " + htmlFolder);
        }
        for (ViewDto viewDto: applicationDto.getViewDtos()) {
            String result = generateHtml(viewDto);
            String htmlFIle = htmlFolder + "/" + viewDto.getName() + ".html";
            File file = new File(htmlFIle);
            writeToFile(file, result);
        }
        log.exiting(HtmlGeneratorService.class.getSimpleName(),"generateMainDartFile");
    }

    String generateHtml(ViewDto viewDto) throws GenerationServiceException {
        return templateService.processTemplate(HTML_VIEW_TEMPLATE, viewDto, "viewDto");
    }
}
