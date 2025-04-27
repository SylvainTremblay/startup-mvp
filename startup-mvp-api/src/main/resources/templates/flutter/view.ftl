<#import "/flutter/flutter-commons.ftl" as flutter>
<#import "/flutter/widget.ftl" as widget>
import 'package:flutter/material.dart';

class ${object.getName()} extends <#if object.getStateless()>StatelessWidget<#else>StatefulWidget</#if> {
  const ${object.getName()}({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return <@widget.widget object.getMainWidgetDto() true/>;
  }
}
