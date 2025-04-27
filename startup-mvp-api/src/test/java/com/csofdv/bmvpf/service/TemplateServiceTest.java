package com.csofdv.bmvpf.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.csofdv.bmvpf.exception.GenerationServiceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.Writer;

@SpringBootTest
public class TemplateServiceTest {

    static {
        System.setProperty("VERTEXAI_PROJECT_ID", "foo");
    }

    @Mock
    private Configuration freemarketConfiguration;

    @InjectMocks
    private TemplateService templateService;

    @BeforeEach
    public void setUp() throws Exception {
        Template template = mock(Template.class);
        when(freemarketConfiguration.getTemplate(anyString())).thenReturn(template);
        doAnswer(invocation -> {
            Writer writer = invocation.getArgument(1);
            writer.write("Processed template content");
            return null;
        }).when(template).process(any(), any(Writer.class));
    }

    @Test
    public void shouldProcessTemplateSuccessfully() throws Exception {
        String result = templateService.processTemplate("templateName", "parameterValue", "parameterName");
        assertEquals("Processed template content", result);
    }

    @Test
    public void shouldThrowRuntimeExceptionOnIOException() throws Exception {
        when(freemarketConfiguration.getTemplate(anyString())).thenThrow(new IOException());
        assertThrows(GenerationServiceException.class, () -> templateService.processTemplate("templateName", "parameterValue", "parameterName"));
    }

    @Test
    public void shouldThrowRuntimeExceptionOnTemplateException() throws Exception {
        Template template = mock(Template.class);
        when(freemarketConfiguration.getTemplate(anyString())).thenReturn(template);
        doThrow(new freemarker.template.TemplateException("Error processing template", null)).when(template).process(any(), any(Writer.class));

        assertThrows(GenerationServiceException.class, () -> templateService.processTemplate("templateName", "parameterValue", "parameterName"));
    }
}