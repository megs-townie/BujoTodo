import 'package:flutter/material.dart';

void main() {
  runApp(CalculatorApp());
}

class CalculatorApp extends StatefulWidget {
  const CalculatorApp({Key? key}) : super(key: key);

  @override
  _CalculatorAppState createState() => _CalculatorAppState();
}

class _CalculatorAppState extends State<CalculatorApp> {
  late double num1, num2, answer = 0;

  // get onPressed => null;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
            appBar: AppBar(
              title: Center(child: Text("Tim's Groovy Calculator App!")),
              backgroundColor: Colors.teal,
            ),
            body: Column(
              children: <Widget>[
                Text(
                  "1st number",
                  style: TextStyle(fontSize: 30),
                ),
                TextField(
                  onChanged: (value) => num1 = double.parse(value),
                ),
                Text(
                  "2nd number",
                  style: TextStyle(fontSize: 30),
                ),
                TextField(
                  onChanged: (value) => num2 = double.parse(value),
                ),
                Row(
                  children: <Widget>[
                    Expanded(
                      child: ElevatedButton(
                        style: ButtonStyle(
                            backgroundColor:
                                MaterialStateProperty.all(Colors.tealAccent)),
                        child: Text("+"),
                        onPressed: () {
                          setState(() {
                            answer = num1 + num2;
                          });
                        },
                      ),
                    ),
                    Expanded(
                      child: ElevatedButton(
                        style: ButtonStyle(
                            backgroundColor:
                                MaterialStateProperty.all(Colors.teal)),
                        child: Text("-"),
                        onPressed: () {
                          setState(() {
                            answer = num1 - num2;
                          });
                        },
                      ),
                    ),
                    Expanded(
                      child: ElevatedButton(
                        style: ButtonStyle(
                            backgroundColor:
                                MaterialStateProperty.all(Colors.tealAccent)),
                        child: Text("x"),
                        onPressed: () {
                          setState(() {
                            answer = num1 * num2;
                          });
                        },
                      ),
                    ),
                    Expanded(
                      child: ElevatedButton(
                        style: ButtonStyle(
                            backgroundColor:
                                MaterialStateProperty.all(Colors.teal)),
                        child: Text("/"),
                        onPressed: () {
                          setState(() {
                            answer = num1 / num2;
                          });
                        },
                      ),
                    )
                  ],
                ),
                Text(answer.toString(), style: TextStyle(fontSize: 30))
              ],
            )));
  }
}
