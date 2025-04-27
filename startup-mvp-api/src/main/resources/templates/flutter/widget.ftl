<#import "/flutter/flutter-commons.ftl" as flutter>
<#if object.getClass().getSimpleName() == "WidgetDto">
    <@widget object true/>
</#if>
<#macro widget object topLevel>
    <#local widgetDto = object>
    <#if widgetDto.getConstant()>const </#if>${widgetDto.getWidgetType().getName()}(
    <#-- Print out actions before attributes -->
    <#list widgetDto.getActions() as actionKey, actionValue>
        <#if actionValue??>
            ${actionKey}: ${actionValue},
        </#if>
    </#list>
    <#list widgetDto.getAttributes() as attributeKey, attributeValue>
        <#assign attribute = widgetDto.getWidgetType().getAttributeTypes()[attributeKey]>
        <#assign typeName = attribute.getAttributeType().getName()>
        <#if attributeValue??>
            <#if typeName == "String">
              <#if attributeValue?starts_with("$")>
                <#-- Slice the first character to remove the $ from the string -->
                ${attributeValue[1..]},
              <#elseif attributeValue?starts_with("'")>
                ${attributeValue},
              <#else>
                <#if attribute.getAttributeNameRequired()>${attributeKey}: </#if>'${attributeValue}',
              </#if>
            <#elseif typeName == "Widget">
              ${attributeKey}: <@widget attributeValue false/><#rt>
            <#elseif typeName == "Array" && attributeValue?size != 0>
                ${attributeKey}: <Widget> [
                    <#list attributeValue as item>
                    <@widget item false/>
                    </#list>
                ], // <Widget> []
            <#elseif typeName == "Class">
              <#if attribute.getAttributeNameRequired()>${attributeKey}: </#if>${attributeValue},
            </#if>
        </#if>
    </#list>
    )<#if topLevel>;<#else>,</#if> // ${widgetDto.getWidgetType().getName()}
</#macro>