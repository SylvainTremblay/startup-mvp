<#macro widget widgetDto>
<#if widgetDto.getWidgetType().getName() == "comment">
    <#-- Special handling for comment widget -->
    <!--     ${widgetDto.getAttributes()["comment"]?default("")} -->
<#elseif widgetDto.getWidgetType().getName() == "text">
    <#-- Special handling for text widget -->
    ${widgetDto.getAttributes()["text"]?default("")}
<#else>
    <${widgetDto.getWidgetType().getName()}<#list widgetDto.getAttributes() as attributeKey, attributeValue><#if attributeKey != "child"> ${attributeKey}="${attributeValue}"</#if></#list>>
    <#if widgetDto.getAttributes()["child"]??>
        <#assign child = widgetDto.getAttributes()["child"]>
        <#if child??>
            <#list child as aChild>
                <@widget aChild/>
            </#list>
        </#if>
    </#if>
    </${widgetDto.getWidgetType().getName()}>
</#if>
</#macro>