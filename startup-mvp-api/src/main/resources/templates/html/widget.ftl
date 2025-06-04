<#macro widget widgetDto>
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
</#macro>