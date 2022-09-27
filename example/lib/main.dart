import 'dart:async';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:scanner/scanner.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _scannerPlugin = Scanner();
  String? _scannerProps;
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    try {
      await _scannerPlugin.initScanner().then((_) async {
        try {
          String? props = await _scannerPlugin.getProps();
          log('Scanner props: $props');
          setState(() => _scannerProps = props);
        } catch (e) {
          log('Failed to get scanner props: ${e.toString()}');
        }
      });
    } catch (e) {
      inspect(e);
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: Column(
            children: [
              Text('Scanner props: $_scannerProps'),
              TextButton(
                onPressed: () async {
                  try {
                    final result = await _scannerPlugin.openScanner();
                    log(result!);
                  } catch (e) {
                    inspect(e);
                  }
                },
                child: const Text('Open'),
              ),
            ],
          )),
    );
  }
}
