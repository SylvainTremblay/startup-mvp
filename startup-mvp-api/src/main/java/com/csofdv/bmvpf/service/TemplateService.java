package com.csofdv.bmvpf.service;

import com.csofdv.bmvpf.exception.GenerationServiceException;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateService {

    @Autowired
    private Configuration freemakerConfiguration;

    @PostConstruct
    public void init() {
        freemakerConfiguration.setTemplateExceptionHandler(new TemplateExceptionHandler(){
            @Override
            public void handleTemplateException(TemplateException te, Environment env, java.io.Writer out) throws TemplateException {
                try {
                    out.write("[ERROR: " + te.getMessage() + "]");
                } catch (IOException e) {
                    throw new TemplateException("Failed to print error message. Cause: " + e, env);
                }
            }
        });
    }
    public String processWidgetTemplate(String templateName, Object object) throws GenerationServiceException {

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("object", object);
        return processTemplate(templateName, input);
    }

    public String processTemplate(String templateName, Object parameter, String parameterName) throws GenerationServiceException {

        Map<String, Object> input = new HashMap<String, Object>();
        input.put(parameterName, parameter);
        return processTemplate(templateName, input);
    }

    public String processTemplate(String templateName, Map<String, Object> parameters) throws GenerationServiceException {

        try {
            Template template = freemakerConfiguration.getTemplate(templateName);
            StringWriter output = new StringWriter();
            template.process(parameters, output);
            return output.getBuffer().toString();
        } catch (IOException|TemplateException e) {
            throw new GenerationServiceException(e);
        }
    }
}
