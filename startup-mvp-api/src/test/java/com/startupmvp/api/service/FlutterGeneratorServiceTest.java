package com.startupmvp.api.service;

import com.startupmvp.api.dto.ApplicationDto;
import com.startupmvp.api.dto.AttributeTypeDto;
import com.startupmvp.api.dto.OrganizationDto;
import com.startupmvp.api.dto.ViewDto;
import com.startupmvp.api.dto.WidgetDto;
import com.startupmvp.api.dto.WidgetTypeAttributeDto;
import com.startupmvp.api.dto.WidgetTypeDto;
import com.startupmvp.api.exception.GenerationServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class FlutterGeneratorServiceTest {

    @Autowired
    private FlutterGeneratorService flutterGeneratorService;

    @Test
    public void testGenerateApplication() throws GenerationServiceException {
        // Given
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.setOrganizationDto(new OrganizationDto());
        applicationDto.getOrganizationDto().setOrganizationName("com.csofdv");
        applicationDto.setApplicationName("test_app");
        applicationDto.setApplicationId(UUID.randomUUID().toString());

        // Create main widget
        AttributeTypeDto attributeTypeDto = new AttributeTypeDto();
        attributeTypeDto.setName("String");
        WidgetTypeAttributeDto widgetTypeAttributeDto = new WidgetTypeAttributeDto();
        widgetTypeAttributeDto.setAttributeName("data");
        widgetTypeAttributeDto.setAttributeType(attributeTypeDto);
        WidgetTypeDto textWidgetTypeDto = new WidgetTypeDto("Text");
        textWidgetTypeDto.addAttributeType(widgetTypeAttributeDto);

        WidgetDto textWidgetDto = new WidgetDto(textWidgetTypeDto);
        textWidgetDto.setConstant(true);
        textWidgetDto.getAttributes().put("data", "Hello");

        ViewDto viewDto = new ViewDto();
        viewDto.setMainView(true);
        viewDto.setName("MainView");
        viewDto.setMainWidgetDto(textWidgetDto);
        applicationDto.getViewDtos().add(viewDto);
        // When
        String result = flutterGeneratorService.generateApplication(applicationDto);

        // Then
    }
}