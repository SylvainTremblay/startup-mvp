package com.startupmvp.api.repository;

import com.startupmvp.api.model.AttributeType;
import com.startupmvp.api.model.Platform;
import com.startupmvp.api.model.Widget;
import com.startupmvp.api.model.WidgetAttributeValue;
import com.startupmvp.api.model.WidgetType;
import com.startupmvp.api.model.WidgetTypeAttribute;
import com.startupmvp.api.service.OrganizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class WidgetRepositoryTest {
    @Autowired
    private WidgetRepository repository;

    @Autowired
    private WidgetTypeRepository widgetTypeRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private AttributeTypeRepository attributeRepository;

    @Test
    void contextLoads() {
        assertThat(repository).isNotNull();
    }

    // Load widget type from the repository
    // Create widget entity, save it and find it by Id.
    @Test
    public void testInsertIFindDeleteWidget() {

        // create String attribute type
        AttributeType stringAttributeType = new AttributeType();
        stringAttributeType.setName("String");
        attributeRepository.save(stringAttributeType);
        // Create platform entity
        Platform platform = new Platform();
        platform.setPlatformCode("HTML5");
        platform.setPlatformName("HTML5 Platform");
        platform.setVersionDate(LocalDate.now());
        platform.setPlatformVersion("1.0");
        platform = platformRepository.save(platform);
        // Create widget type entity
        WidgetType htmlWidgetType = new WidgetType();
        htmlWidgetType.setName("html");
        htmlWidgetType.setPlatform(platform);
        // Add attributes to the widget type
        WidgetTypeAttribute titleAttributeType = new WidgetTypeAttribute();
        titleAttributeType.setWidgetType(htmlWidgetType);
        titleAttributeType.setAttributeName("title");
        titleAttributeType.setAttributeType(stringAttributeType);
        htmlWidgetType = widgetTypeRepository.save(htmlWidgetType);
        // Create widget entity
        Widget widget = new Widget();
        widget.setWidgetType(htmlWidgetType);
        widget.setConstant(Boolean.FALSE);
        widget.setTimestamp(LocalDateTime.now());
        WidgetAttributeValue titleAttributeValue = new WidgetAttributeValue();
        titleAttributeValue.setAttributeType(stringAttributeType);
        titleAttributeValue.setAttributeValue("Test Widget Title");
        titleAttributeValue.setWidget(widget);
        widget.setAttributeValues(new java.util.ArrayList<>());
        widget.getAttributeValues().add(titleAttributeValue);
        // Save widget entity
        Widget savedWidget = repository.save(widget);

        // Load the widget by ID
        Widget foundWidget = repository.findById(savedWidget.getWidgetId()).orElse(null);

        // Verify that the widget was saved correctly
        assertThat(foundWidget).isNotNull();
        assertThat(foundWidget.getConstant()).isEqualTo(widget.getConstant());
        assertThat(foundWidget.getWidgetType()).isNotNull();
        assertThat(foundWidget.getWidgetType().getWidgetTypeId()).isEqualTo(widget.getWidgetType().getWidgetTypeId());
        assertThat(foundWidget.getAttributeValues()).hasSize(1);
        // Clean up by deleting the widget
        repository.deleteById(savedWidget.getWidgetId());
    }
}
