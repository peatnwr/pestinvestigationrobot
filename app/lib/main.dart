import 'package:flutter/material.dart';

void main(List<String> args) {
  var app = mainApp();
  runApp(app);
}

class mainApp extends StatelessWidget {
  const mainApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Pest Investigation Application",
      home: Scaffold(
        appBar: AppBar(
          title: Text("Pi Application"),
        ),
        body: Text("Hello Body"),
      ),
      theme: ThemeData(primarySwatch: Colors.green),
    );
  }
}
