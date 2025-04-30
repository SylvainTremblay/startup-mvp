package com.csofdv.bmvpf.service;

import static org.junit.jupiter.api.Assertions.*;

import com.csofdv.bmvpf.dto.ApplicationDto;
import com.csofdv.bmvpf.dto.AttributeTypeDto;
import com.csofdv.bmvpf.dto.AttributeTypeValueDto;
import com.csofdv.bmvpf.dto.ViewDto;
import com.csofdv.bmvpf.dto.WidgetDto;
import com.csofdv.bmvpf.dto.WidgetTypeActionDto;
import com.csofdv.bmvpf.dto.WidgetTypeAttributeDto;
import com.csofdv.bmvpf.dto.WidgetTypeDto;
import freemarker.template.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class TemplateServiceIntegrationTest {

    @Autowired
    private Configuration freemarketConfiguration;

    @Autowired
    private TemplateService templateService;

    private final AttributeTypeDto stringAttributeType = createAttributeTypeDto("String");
    private final AttributeTypeDto intAttributeType = createAttributeTypeDto("int");
    private final AttributeTypeDto classAttributeType = createAttributeTypeDto("Class");
    private final AttributeTypeDto iconsAttributeType = createAttributeTypeDto("Icons", true);
    private final AttributeTypeDto mainAxisAlignmentAttributeType = createAttributeTypeDto("MainAxisAlignment", true);
    private final AttributeTypeDto widgetAttributeType = createAttributeTypeDto("Widget");
    private final AttributeTypeDto arrayAttributeType = createAttributeTypeDto("Array");

    private static AttributeTypeDto createAttributeTypeDto(String name) {
        return createAttributeTypeDto(name, false);
    }

    private static AttributeTypeDto createAttributeTypeDto(String name, boolean isEnumeration) {
        AttributeTypeDto attributeTypeDto = new AttributeTypeDto();
        attributeTypeDto.setName(name);
        attributeTypeDto.setEnumeration(isEnumeration);
        return attributeTypeDto;
    }

    private static WidgetTypeAttributeDto createWidgetTypeAttributeDto(String name, AttributeTypeDto attributeTypeDto) {
        WidgetTypeAttributeDto widgetTypeAttributeDto = new WidgetTypeAttributeDto();
        widgetTypeAttributeDto.setAttributeName(name);
        widgetTypeAttributeDto.setAttributeType(attributeTypeDto);
        return widgetTypeAttributeDto;
    }

    private static WidgetTypeActionDto createWidgetTypeActionDto(String name) {
        WidgetTypeActionDto widgetTypeActionDto = new WidgetTypeActionDto();
        widgetTypeActionDto.setName(name);
        return widgetTypeActionDto;
    }

    private WidgetTypeDto createAppBarWidgetType() {

        WidgetTypeDto appBarWidgetTypeDto = new WidgetTypeDto("AppBar");

        WidgetTypeAttributeDto colorTypeAttribute = createWidgetTypeAttributeDto("backgroundColor", classAttributeType);
        appBarWidgetTypeDto.addAttributeType(colorTypeAttribute);
        appBarWidgetTypeDto.getAttributeTypes().get("backgroundColor").setAttributeNameRequired(true);
        WidgetTypeAttributeDto titleTypeAttribute = createWidgetTypeAttributeDto("title", widgetAttributeType);
        appBarWidgetTypeDto.addAttributeType(titleTypeAttribute);

        return appBarWidgetTypeDto;
    }

    private WidgetDto createTextWidget(String data, boolean constant) {
        WidgetTypeDto textWidgetTypeDto = new WidgetTypeDto("Text");
        textWidgetTypeDto.addAttributeType(createWidgetTypeAttributeDto("data", stringAttributeType));

        WidgetDto textWidgetDto = new WidgetDto(textWidgetTypeDto);
        textWidgetDto.setConstant(constant);
        textWidgetDto.getAttributes().put("data", data);
        return textWidgetDto;
    }

    private WidgetDto createAppBarWidget() {

        WidgetDto widgetDto = new WidgetDto(createAppBarWidgetType());
        widgetDto.getAttributes().put("title", createTextWidget("$widget.title", false));
        widgetDto.getAttributes().put("backgroundColor", "Theme.of(context).colorScheme.inversePrimary");

        return widgetDto;
    }

    private WidgetDto createScaffoldWidget() {
        WidgetTypeDto scaffoldWidgetTypeDto = new WidgetTypeDto("Scaffold");
        scaffoldWidgetTypeDto.addAttributeType(createWidgetTypeAttributeDto("appBar", widgetAttributeType));
        scaffoldWidgetTypeDto.addAttributeType(createWidgetTypeAttributeDto("body", widgetAttributeType));
        scaffoldWidgetTypeDto.addAttributeType(createWidgetTypeAttributeDto("floatingActionButton", widgetAttributeType));

        WidgetDto scaffoldWidget = new WidgetDto(scaffoldWidgetTypeDto);
        scaffoldWidget.setAttribute("appBar", createAppBarWidget());
        scaffoldWidget.setAttribute("body", createCenterWidget());
        scaffoldWidget.setAttribute("floatingActionButton", createButtonWidget());
        return scaffoldWidget;
    }

    @Test
    public void shouldProcessScaffoldWidgetTemplateSuccessfully() throws Exception {

        WidgetDto scaffoldWidget = createScaffoldWidget();
        String result = templateService.processWidgetTemplate("flutter/widget.ftl", scaffoldWidget);
        System.out.println(result);
    }

    private WidgetDto createIconWidget() {
        WidgetTypeDto iconWidgetTypeDto = new WidgetTypeDto("Icon");
        iconWidgetTypeDto.addAttributeType(createWidgetTypeAttributeDto("icon", iconsAttributeType));
        WidgetDto iconWidget = new WidgetDto(iconWidgetTypeDto);
        AttributeTypeValueDto iconAdd = new AttributeTypeValueDto();
        iconAdd.setAttributeValue("add");
        iconWidget.setAttribute("icon", iconAdd);
        return iconWidget;
    }

    private WidgetDto createButtonWidget() {
        WidgetTypeDto buttonWidgetTypeDto = new WidgetTypeDto("FloatingActionButton");
        WidgetTypeAttributeDto tooltipTypeAttribute = createWidgetTypeAttributeDto("tooltip", stringAttributeType);
        tooltipTypeAttribute.setAttributeNameRequired(true);
        buttonWidgetTypeDto.addAttributeType(tooltipTypeAttribute);
        buttonWidgetTypeDto.addAttributeType(createWidgetTypeAttributeDto("child", widgetAttributeType));
        buttonWidgetTypeDto.addAction(createWidgetTypeActionDto("onPressed"));
        WidgetDto buttonWidget = new WidgetDto(buttonWidgetTypeDto);

        buttonWidget.setAction("onPressed", "_incrementCounter");
        buttonWidget.setAttribute("tooltip", "Increment");
        buttonWidget.setAttribute("child", createIconWidget());
        // TODO constructor vs property??? String property vs constructor parameter???
        //buttonWidget.setAttribute("child", createTextWidget("+", false));
        return  buttonWidget;
    }

    private WidgetDto createCenterWidget() {

        WidgetTypeDto columnWidgetTypeDto = new WidgetTypeDto("Column");
        columnWidgetTypeDto.addAttributeType(createWidgetTypeAttributeDto("mainAxisAlignment", mainAxisAlignmentAttributeType));
        columnWidgetTypeDto.getAttributeTypes().get("mainAxisAlignment").setAttributeNameRequired(true);
        columnWidgetTypeDto.addAttributeType(createWidgetTypeAttributeDto("children", arrayAttributeType));

        WidgetDto columnWidget = new WidgetDto(columnWidgetTypeDto);
        AttributeTypeValueDto centerAlignment = new AttributeTypeValueDto();
        centerAlignment.setAttributeValue("center");
        columnWidget.setAttribute("mainAxisAlignment", centerAlignment);
        WidgetDto counterWidget = createTextWidget("'$_counter'", false);
        counterWidget.getWidgetType().addAttributeType(createWidgetTypeAttributeDto("style", classAttributeType));
        counterWidget.getWidgetType().getAttributeTypes().get("style").setAttributeNameRequired(true);
        counterWidget.setAttribute("style", "Theme.of(context).textTheme.headlineMedium");
        columnWidget.setAttribute("children", new WidgetDto[]{createTextWidget("You have pushed the button this many times:", true), counterWidget});

        WidgetTypeDto centerWidgetTypeDto = new WidgetTypeDto("Center");
        centerWidgetTypeDto.addAttributeType(createWidgetTypeAttributeDto("child", widgetAttributeType));

        WidgetDto centerWidgetDto = new WidgetDto(centerWidgetTypeDto);
        centerWidgetDto.setAttribute("child", columnWidget);

        return centerWidgetDto;
    }

    @Test
    public void shouldProcessAppBarWidgetTemplateSuccessfully() throws Exception {
        WidgetDto widgetDto = createAppBarWidget();
        String result = templateService.processWidgetTemplate("flutter/widget.ftl", widgetDto);
        System.out.println(result);
        String[] lines = result.split("\n");
        assertEquals(6, lines.length);
        assertEquals("AppBar(", lines[0].trim());

        assertTrue(lines[1].trim().startsWith("title:"));
        assertTrue(lines[1].endsWith("Text("));
        assertEquals("widget.title,", lines[2].trim());
        assertEquals("), // Text", lines[3].trim());
        assertEquals("backgroundColor: Theme.of(context).colorScheme.inversePrimary,", lines[4].trim());
        assertEquals("); // AppBar", lines[5].trim());
    }

    @Test
    public void shouldProcessTextWidgetTemplateSuccessfully() throws Exception {
        WidgetTypeDto widgetTypeDto = new WidgetTypeDto("Text");

        WidgetTypeAttributeDto dataTypeAttribute = createWidgetTypeAttributeDto("data", stringAttributeType);
        widgetTypeDto.addAttributeType(dataTypeAttribute);

        WidgetTypeAttributeDto maxLinesTypeAttribute = createWidgetTypeAttributeDto("maxLines", intAttributeType);
        widgetTypeDto.addAttributeType(maxLinesTypeAttribute);

        WidgetTypeAttributeDto styleTypeAttribute = createWidgetTypeAttributeDto("style", classAttributeType);
        styleTypeAttribute.setAttributeNameRequired(true);
        widgetTypeDto.addAttributeType(styleTypeAttribute);

        WidgetDto widgetDto = new WidgetDto();
        widgetDto.setConstant(Boolean.TRUE);
        widgetDto.setWidgetType(widgetTypeDto);
        widgetDto.getAttributes().put("data", "HELLO");
        widgetDto.getAttributes().put("style", "Theme.of(context).textTheme.headlineMedium");
        widgetDto.getAttributes().put("maxLines", "10");
        String result = templateService.processWidgetTemplate("flutter/widget.ftl", widgetDto);
        System.out.println(result);
        String[] lines = result.split("\n");
        assertEquals(4, lines.length);
        assertEquals("const Text(", lines[0].trim());
        assertEquals("'HELLO',", lines[1].trim());
        assertEquals("style: Theme.of(context).textTheme.headlineMedium,", lines[2].trim());
        assertEquals("); // Text", lines[3].trim());
    }

    @Test
    public void shouldProcessViewDtoTemplateSuccessfully() throws Exception {

        ViewDto viewDto = new ViewDto();
        viewDto.setStateless(Boolean.TRUE);
        viewDto.setMainWidgetDto(createAppBarWidget());
        viewDto.setName("MainView");

        String result = templateService.processTemplate("flutter/view.ftl", viewDto, "object");
        System.out.println(result);
        assertTrue(result.contains("class MainView"));
        assertTrue(result.contains("extends StatelessWidget"));
    }

    @Test
    public void shouldProcessApplicationDtoTemplateSuccessfully() throws Exception {

        ViewDto viewDto = new ViewDto();
        viewDto.setStateless(Boolean.TRUE);
        viewDto.setName("MainView");
        viewDto.setMainWidgetDto(createAppBarWidget());
        viewDto.setMainView(Boolean.TRUE);

        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.getViewDtos().add(viewDto);
        applicationDto.setApplicationName("SampleApp");

        String result = templateService.processTemplate("flutter/main.ftl", applicationDto, "object");
        System.out.println(result);
        assertTrue(result.contains("class SampleApp"));
        assertTrue(result.contains("extends StatelessWidget"));
        assertTrue(result.contains("void main()"));
    }
}