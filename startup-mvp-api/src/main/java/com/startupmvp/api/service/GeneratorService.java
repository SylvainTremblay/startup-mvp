package com.startupmvp.api.service;

import com.startupmvp.api.dto.ApplicationDto;
import com.startupmvp.api.exception.GenerationServiceException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

@Service
@Log
public abstract class GeneratorService {

    @Autowired
    protected TemplateService templateService;

    private String generationFolder = "/tmp";

    /**
     * Generates application code and return the path to the generated application.
     *
     * @param applicationDto
     * @return
     * @throws GenerationServiceException
     */
    public String generateApplication(ApplicationDto applicationDto) throws GenerationServiceException {

        log.entering(this.getClass().getSimpleName(),"generateApplication", applicationDto.getApplicationName());
        validateBeforeGeneration(applicationDto);
        String applicationFolder = generationFolder + "/" + applicationDto.getApplicationId();
        try {
            if (!new File(applicationFolder).mkdirs()) {
                log.info("Application folder already exists: " + applicationFolder);
            } else {
                log.info("Application folder created: " + applicationFolder);
            }
        } catch (SecurityException e) {
            String errorMessage = "Error creating application folder: " + e.getMessage();
            log.log(Level.SEVERE, errorMessage, e);
            throw new GenerationServiceException(errorMessage, e);
        }
        generateApplicationCode(applicationDto, applicationFolder);
        log.exiting(this.getClass().getSimpleName(),"generateApplication");
        return null;
    }

    abstract void generateApplicationCode(ApplicationDto applicationDto, String applicationFolder) throws GenerationServiceException;

    void validateBeforeGeneration(ApplicationDto applicationDto) throws GenerationServiceException {

        // We use the application ID as the folder name
        if (applicationDto.getApplicationId() == null || applicationDto.getApplicationId().isEmpty()) {
            String errorMessage = "Application ID is null or empty";
            log.log(Level.SEVERE, errorMessage);
            throw new GenerationServiceException(errorMessage);
        }
        // Start by generating the app skeleton
        if (applicationDto.getApplicationName() == null || applicationDto.getApplicationName().isEmpty()) {
            String errorMessage = "Application name is null or empty";
            log.log(Level.SEVERE, errorMessage);
            throw new GenerationServiceException(errorMessage);
        }
        if (applicationDto.getOrganizationDto() == null) {
            String errorMessage = "Organization is null";
            log.log(Level.SEVERE, errorMessage);
            throw new GenerationServiceException(errorMessage);
        }
        if (applicationDto.getOrganizationDto().getOrganizationName() == null || applicationDto.getOrganizationDto().getOrganizationName().isEmpty()) {
            String errorMessage = "Organization name is null or empty";
            log.log(Level.SEVERE, errorMessage);
            throw new GenerationServiceException(errorMessage);
        }
        if (applicationDto.getMainViewDto() == null) {
            String errorMessage = "Main view is null";
            log.log(Level.SEVERE, errorMessage);
            throw new GenerationServiceException(errorMessage);
        }
        if (applicationDto.getMainViewDto().getMainWidgetDto() == null) {
            String errorMessage = "Main widget of Main View is null";
            log.log(Level.SEVERE, errorMessage);
            throw new GenerationServiceException(errorMessage);
        }
    }

    void writeToFile(File file, String result) throws GenerationServiceException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(result);
        } catch (IOException e) {
            String errorMessage = "Error writing to file: " + e.getMessage();
            log.log(Level.SEVERE, errorMessage, e);
            throw new GenerationServiceException(errorMessage, e);
        }
    }

}
