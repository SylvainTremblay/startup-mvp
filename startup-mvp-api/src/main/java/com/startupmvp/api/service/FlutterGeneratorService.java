package com.startupmvp.api.service;

import com.startupmvp.api.dto.ApplicationDto;
import com.startupmvp.api.dto.ViewDto;
import com.startupmvp.api.exception.GenerationServiceException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

@Service
@Log
public class FlutterGeneratorService extends GeneratorService {

    public static final String MAIN_DART_FILE_TEMPLATE = "flutter/main.ftl";
    public static final String VIEW_DART_FILE_TEMPLATE = "flutter/view.ftl";
    public static final String WIDGET_DART_FILE_TEMPLATE = "flutter/widget.ftl";
    public static final String MAIN_DART_FILE_LOCATION = "lib/main.dart";

    /**
     * Generates the skeleton of the Flutter application using View CLI create method.
     *
     * @param applicationDto Contains the application name and other details.
     * @param applicationFolder The folder where the application is generated.
     * @return The exit code of the process.
     */
    void generateApplicationCode(ApplicationDto applicationDto, String applicationFolder) throws GenerationServiceException {
        log.entering(this.getClass().getSimpleName(),"generateApplicationCode", applicationDto.getApplicationName());
        int exitCode = -1;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(applicationFolder));
            processBuilder.command("flutter", "create", applicationDto.getApplicationName(), "--org", applicationDto.getOrganizationDto().getOrganizationName());
            Process process = processBuilder.start();
            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
            exitCode = process.waitFor();
            if (exitCode != 0) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String errorline;
                while ((errorline = errorReader.readLine()) != null) {
                    log.severe(errorline);
                }

            }
        } catch (IOException|InterruptedException e) {
            log.log(Level.SEVERE, "Error generating Flutter app skeleton: " + e.getMessage(), e);
        }
        if (exitCode != 0) {
            String errorMessage = "Error generating Flutter app skeleton. Exit code: " + exitCode;
            throw new GenerationServiceException(errorMessage);
        }
        // Now generate the main.dart file
        generateMainDartFile(applicationDto, applicationFolder);
        // Now generate the views
        generateViews(applicationDto, applicationFolder);
        log.exiting(this.getClass().getSimpleName(),"generateApplicationCode");
    }

    /**
     * Generates the main.dart file for the Flutter application and replace the default one.
     *
     * @param applicationDto Contains the application name and other details.
     * @param applicationFolder The folder where the application is generated.
     */
    void generateMainDartFile(ApplicationDto applicationDto, String applicationFolder) throws GenerationServiceException {
        log.entering(FlutterGeneratorService.class.getSimpleName(),"generateMainDartFile", applicationDto.getApplicationName());
        String result = templateService.processTemplate("flutter/main.ftl", applicationDto, "object");
        // Replace the main.dart file in the generated application
        String mainDartFile = applicationFolder + "/" + applicationDto.getApplicationName() + "/" + MAIN_DART_FILE_LOCATION;
        File file = new File(mainDartFile);
        writeToFile(file, result);
        log.exiting(FlutterGeneratorService.class.getSimpleName(),"generateMainDartFile");
    }

    /**
     * Generates the views for the Flutter application in the views folder.
     *
     * @param applicationDto Contains the application name and other details.
     * @param applicationFolder The folder where the application is generated.
     */
    private void generateViews(ApplicationDto applicationDto, String applicationFolder) throws GenerationServiceException {
        log.entering(FlutterGeneratorService.class.getSimpleName(),"generateViews", applicationDto.getApplicationName());
        // First create the views folder if it doesn't exist
        String viewsFolder = applicationFolder + "/" + applicationDto.getApplicationName() + "/lib/views";
        File viewsDir = new File(viewsFolder);
        if (!viewsDir.exists()) {
            if (viewsDir.mkdirs()) {
                log.info("Views folder created: " + viewsFolder);
            } else {
                String errorMessage = "Error creating views folder: " + viewsFolder;
                log.log(Level.SEVERE, errorMessage);
                throw new GenerationServiceException(errorMessage);
            }
        } else {
            log.info("Views folder already exists: " + viewsFolder);
        }
        for (ViewDto viewDto : applicationDto.getViewDtos()) {
            log.info("Generating view: " + viewDto.getName());
            String result = templateService.processWidgetTemplate(VIEW_DART_FILE_TEMPLATE, viewDto);
            // Replace the view.dart file in the generated application
            String viewDartFile = applicationFolder + "/" + applicationDto.getApplicationName() + "/lib/views/" + viewDto.getName() + ".dart";
            File file = new File(viewDartFile);
            writeToFile(file, result);
        }
        log.exiting(FlutterGeneratorService.class.getSimpleName(),"generateViews");
    }
}
