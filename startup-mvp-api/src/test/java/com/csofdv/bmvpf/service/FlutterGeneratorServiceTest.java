package com.csofdv.bmvpf.service;

import com.csofdv.bmvpf.dto.ApplicationDto;
import com.csofdv.bmvpf.dto.AttributeTypeDto;
import com.csofdv.bmvpf.dto.OrganizationDto;
import com.csofdv.bmvpf.dto.ViewDto;
import com.csofdv.bmvpf.dto.WidgetDto;
import com.csofdv.bmvpf.dto.WidgetTypeAttributeDto;
import com.csofdv.bmvpf.dto.WidgetTypeDto;
import com.csofdv.bmvpf.exception.GenerationServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class FlutterGenerationServiceTest {

    @Autowired
    private FlutterGenerationService flutterGenerationService;

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
        String result = flutterGenerationService.generateApplication(applicationDto);

        // Then
    }
}