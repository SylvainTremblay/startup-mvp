package com.smvp.tools.htmlspec.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HtmlSpecDbLoader {
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("HtmlSpecDbLoader started.");
        ObjectMapper mapper = new ObjectMapper();

        // 1. Load global attributes from resources
        Set<String> globalAttributes = parseGlobalAttributes();

        // 2. Get directory of element JSON files
        List<HtmlElement> htmlElements = parseHtmlElements(globalAttributes);

        // 3. Generate liquibase data insert file
        createLiquibaseInsertFile(htmlElements);
    }

    private static Set<String> parseGlobalAttributes() throws IOException {
        Set<String> globalAttributes = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = HtmlSpecDbLoader.class.getClassLoader().getResourceAsStream("global_attributes.json");
        if (inputStream == null) {
            throw new IllegalArgumentException("Could not find global-attributes.json in resources.");
        }
        JsonNode root = mapper.readTree(inputStream);
        JsonNode attributesNode = root.path("html").path("global_attributes");
        Iterator<String> fieldNamesIt = attributesNode.fieldNames();
        while (fieldNamesIt.hasNext()) {
            String attributeName = fieldNamesIt.next();
            JsonNode attributeNode = attributesNode.get(attributeName);
            JsonNode statusNode = attributeNode.get("__compat").get("status");
            if (statusNode != null && !statusNode.get("experimental").asBoolean() && !statusNode.get("deprecated").asBoolean()) {
                globalAttributes.add(attributeName);
            }
        }
        return globalAttributes;
    }

    private static List<HtmlElement> parseHtmlElements(Set<String> globalAttributes) throws IOException, URISyntaxException {
        List<HtmlElement> htmlElements = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        URL url = HtmlSpecDbLoader.class.getClassLoader().getResource("elements");
        if (url == null) {
            throw new IllegalArgumentException("Could not find html-elements directory in resources.");
        }
        File elementsDir = new File(url.toURI());
        File[] files = elementsDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                JsonNode root = mapper.readTree(file);
                HtmlElement htmlElement = parseHtmlElement(root, globalAttributes);
                if (htmlElement != null) {
                    htmlElements.add(htmlElement);
                }
            }
        }
        return htmlElements;
    }

    private static HtmlElement parseHtmlElement(JsonNode root, Set<String> globalAttributes) {
        JsonNode htmlNode = root.path("html").path("elements");
        HtmlElement htmlElement = null;
        if (htmlNode.elements().hasNext()) {
            String tagName = htmlNode.fieldNames().next();
            htmlElement = new HtmlElement(tagName);
            htmlElement.getAttributes().addAll(globalAttributes);
            JsonNode htmlTagElement = htmlNode.get(tagName);
            Iterator<String> attributeIt = htmlTagElement.fieldNames();

            while (attributeIt.hasNext()) {
                String attributeName = attributeIt.next();
                if ("__compat".equals(attributeName)) {
                    // Get Status and skip if experimental or deprecated
                    JsonNode compatNode = htmlTagElement.get(attributeName);
                    if (compatNode != null) {
                        JsonNode statusNode = compatNode.get("status");
                        if (statusNode != null && (statusNode.get("experimental").asBoolean() || statusNode.get("deprecated").asBoolean())) {
                            return null; // Skip elements that are experimental or deprecated
                        }
                    }
                } else if ("no_ua_styles_in_article_aside_nav_section".equals(attributeName)) {
                   // Skip this attribute as it is not relevant for our purposes
                    continue;
                } else {
                    JsonNode attributeNode = htmlTagElement.get(attributeName);
                    JsonNode statusNode = attributeNode.get("__compat").get("status");
                    if (statusNode != null && !statusNode.get("experimental").asBoolean() && !statusNode.get("deprecated").asBoolean()) {
                        htmlElement.getAttributes().add(attributeName);
                    }
                }
            }
        }
        return htmlElement;
    }

    private static void createLiquibaseInsertFile(List<HtmlElement> htmlElements) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<databaseChangeLog xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd\">\n");
        sb.append("  <changeSet author=\"STremblay\" id=\"init-html-elements-1.0\">\n");
        int index = 1;
        for (HtmlElement element : htmlElements) {
            sb.append("    <!-- Insert ").append(element.getName()).append(" HTML Element -->\n");
            sb.append("    <insert tableName=\"widget_type\">\n");
            sb.append("      <column name=\"widget_type_id\">").append(index).append("</column>\n");
            sb.append("      <column name=\"name\">").append(element.getName()).append("</column>\n");
            sb.append("      <column name=\"platform_code\">HTML5</column>\n");
            sb.append("    </insert>\n");
            if (!element.getAttributes().isEmpty()) {
                sb.append("    <!-- Insert ").append(element.getName()).append(" Attributes -->\n");
                for (String attribute : element.getAttributes()) {
                    sb.append("    <insert tableName=\"widget_type_attribute\">\n");
                    sb.append("      <column name=\"attribute_name\">").append(attribute).append("</column>\n");
                    sb.append("      <column name=\"attribute_type_id\">1</column>\n");
                    sb.append("      <column name=\"widget_type_id\">").append(index).append("</column>\n");
                    sb.append("    </insert>\n");
                }
            }
            index++;
        }
        sb.append("  </changeSet>\n");
        sb.append("</databaseChangeLog>\n");
        System.out.println("Generated Liquibase insert file:");
        System.out.println(sb.toString());
    }

}
