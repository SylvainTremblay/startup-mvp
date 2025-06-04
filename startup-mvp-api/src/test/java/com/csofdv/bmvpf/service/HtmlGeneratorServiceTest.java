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
        applicationDto.getOrganizationDto().setOrganizationName("com.csofdv");
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