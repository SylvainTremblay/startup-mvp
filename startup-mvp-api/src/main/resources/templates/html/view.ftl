<#import "/html/widget.ftl" as widget>
<HTML>
<HEAD>
    <TITLE>${viewDto.getName()}</TITLE>
</HEAD>
<@widget.widget viewDto.getMainWidgetDto()/>
</HTML>