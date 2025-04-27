import 'package:flutter/material.dart';
import 'views/${object.getMainViewDto().getName()}.dart';

void main() => runApp(const ${object.getApplicationName()}());

class ${object.getApplicationName()} extends StatelessWidget {
  const ${object.getApplicationName()}({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '${object.getApplicationName()}',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const ${object.getMainViewDto().getName()}(),
    );
  }
}