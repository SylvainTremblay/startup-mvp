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
class HtmlGeneratorServiceTest {

    @Autowired
    private HtmlGeneratorService htmlGeneratorService;

    static final AttributeTypeDto STRING_ATTRIBUTE_TYPE = new AttributeTypeDto("String");
    static final AttributeTypeDto ARRAY_ATTRIBUTE_TYPE = new AttributeTypeDto("Array");

    private WidgetTypeAttributeDto createWidgetTypeAttribute(String attributeName, AttributeTypeDto attributeType) {
        WidgetTypeAttributeDto widgetTypeAttributeDto = new WidgetTypeAttributeDto();
        widgetTypeAttributeDto.setAttributeName(attributeName);
        widgetTypeAttributeDto.setAttributeType(attributeType);
        return widgetTypeAttributeDto;
    }

    @Test
    public void testGenerateHtml() throws GenerationServiceException {
        WidgetTypeDto bodyWidgetTypeDto = new WidgetTypeDto("BODY");
        bodyWidgetTypeDto.addAttributeType(createWidgetTypeAttribute("child", ARRAY_ATTRIBUTE_TYPE));
        // Given
        WidgetTypeDto divWidgetTypeDto = new WidgetTypeDto("DIV");
        divWidgetTypeDto.addAttributeType(createWidgetTypeAttribute("id", STRING_ATTRIBUTE_TYPE));
        divWidgetTypeDto.addAttributeType(createWidgetTypeAttribute("child", ARRAY_ATTRIBUTE_TYPE));

        WidgetDto divWidgetDto = new WidgetDto(divWidgetTypeDto);
        divWidgetDto.getAttributes().put("id", "myFirstDiv");

        WidgetDto childWidgetDto = new WidgetDto(divWidgetTypeDto);
        childWidgetDto.getAttributes().put("id", "childDiv");

        divWidgetDto.setAttribute("child", new WidgetDto[]{childWidgetDto});

        WidgetDto bodyWidgetDto = new WidgetDto(bodyWidgetTypeDto);
        bodyWidgetDto.setAttribute("child", new WidgetDto[]{divWidgetDto});
        ViewDto viewDto = new ViewDto();
        viewDto.setMainView(true);
        viewDto.setName("MainView");
        viewDto.setMainWidgetDto(bodyWidgetDto);
        // When
        String result = htmlGeneratorService.generateHtml(viewDto);
        System.out.println(result);
        // Then
    }

    @Test
    public void testGenerateApplication() throws GenerationServiceException {
        // Given
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.setOrganizationDto(new OrganizationDto());
        applicationDto.getOrganizationDto().setOrganizationName("com.smvp");
        applicationDto.setApplicationName("test_app");
        applicationDto.setApplicationId(UUID.randomUUID().toString());

        // Create main div
        WidgetTypeDto divWidgetTypeDto = new WidgetTypeDto("DIV");
        divWidgetTypeDto.addAttributeType(createWidgetTypeAttribute("id", STRING_ATTRIBUTE_TYPE));
        divWidgetTypeDto.addAttributeType(createWidgetTypeAttribute("class", STRING_ATTRIBUTE_TYPE));

        WidgetDto divWidgetDto = new WidgetDto(divWidgetTypeDto);
        divWidgetDto.getAttributes().put("id", "myFirstDiv");

        ViewDto viewDto = new ViewDto();
        viewDto.setMainView(true);
        viewDto.setName("MainView");
        viewDto.setMainWidgetDto(divWidgetDto);
        applicationDto.getViewDtos().add(viewDto);
        // When
        String result = htmlGeneratorService.generateApplication(applicationDto);
        System.out.println(result);
        // Then
    }
}