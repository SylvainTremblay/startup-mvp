package com.csofdv.bmvpf.service;

import com.csofdv.bmvpf.dto.ApplicationDto;
import com.csofdv.bmvpf.dto.ViewDto;
import com.csofdv.bmvpf.exception.GenerationServiceException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

@Service
@Log
public class FlutterGenerationService extends GeneratorService {

    public static final String MAIN_DART_FILE_TEMPLATE = "flutter/main.ftl";
    public static final String VIEW_DART_FILE_TEMPLATE = "flutter/view.ftl";
    public static final String WIDGET_DART_FILE_TEMPLATE = "flutter/widget.ftl";
    public static final String MAIN_DART_FILE = "lib/main.dart";

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
        } catch (IOException|InterruptedException e) {
            log.log(Level.SEVERE, "Error generating Flutter app skeleton: " + e.getMessage(), e);
        }
        // Now generate the main.dart file
        generateMainDartFile(applicationDto, applicationFolder);
        // Now generate the views
        generateViews(applicationDto, applicationFolder);
        log.exiting(this.getClass().getSimpleName(),"generateApplicationCode");
        if (exitCode != 0) {
            String errorMessage = "Error generating Flutter app skeleton. Exit code: " + exitCode;
            throw new GenerationServiceException(errorMessage);
        }
    }

    /**
     * Generates the main.dart file for the Flutter application and replace the default one.
     *
     * @param applicationDto Contains the application name and other details.
     * @param applicationFolder The folder where the application is generated.
     */
    void generateMainDartFile(ApplicationDto applicationDto, String applicationFolder) throws GenerationServiceException {
        log.entering(FlutterGenerationService.class.getSimpleName(),"generateMainDartFile", applicationDto.getApplicationName());
        String result = templateService.processTemplate("flutter/main.ftl", applicationDto, "object");
        // Replace the main.dart file in the generated application
        String mainDartFile = applicationFolder + "/" + applicationDto.getApplicationName() + "/" + MAIN_DART_FILE;
        File file = new File(mainDartFile);
        writeToFile(file, result);
        log.exiting(FlutterGenerationService.class.getSimpleName(),"generateMainDartFile");
    }

    /**
     * Generates the views for the Flutter application in the views folder.
     *
     * @param applicationDto Contains the application name and other details.
     * @param applicationFolder The folder where the application is generated.
     */
    private void generateViews(ApplicationDto applicationDto, String applicationFolder) throws GenerationServiceException {
        log.entering(FlutterGenerationService.class.getSimpleName(),"generateViews", applicationDto.getApplicationName());
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
        log.exiting(FlutterGenerationService.class.getSimpleName(),"generateViews");
    }

    private void writeToFile(File file, String result) throws GenerationServiceException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(result);
        } catch (IOException e) {
            String errorMessage = "Error writing to file: " + e.getMessage();
            log.log(Level.SEVERE, errorMessage, e);
            throw new GenerationServiceException(errorMessage, e);
        }
    }

}
