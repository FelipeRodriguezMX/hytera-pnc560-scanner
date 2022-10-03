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
      // await _scannerPlugin.init();
      await _scannerPlugin.initScanner();
      inspect(
          await _scannerPlugin.setParams(parameterNumber: '136', value: '99'));
      inspect(
          await _scannerPlugin.setParams(parameterNumber: '137', value: '99'));
      inspect(
          await _scannerPlugin.setParams(parameterNumber: '649', value: '1'));
      final props = await _scannerPlugin.getProps();
      setState(() => _scannerProps = props);
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
                   _scannerPlugin.qrCodeStream?.listen((event) {
                      inspect(event);
                    });
                    inspect(result);
                  } catch (e) {
                    inspect(e);
                  }
                },
                child: const Text('Open'),
              ),
              TextButton(
                onPressed: () async {
                  try {
                    await _scannerPlugin.closeScanner();
                  } catch (e) {
                    inspect(e);
                  }
                },
                child: const Text('Close'),
              ),
           /*   TextButton(
                onPressed: () async {
                  try {
                    await _scannerPlugin.releaseScanner();
                  } catch (e) {
                    inspect(e);
                  }
                },
                child: const Text('Release'),
              ),*/
              /*StreamBuilder(
                stream: _scannerPlugin.qrCodeStream,
                builder: (context, snapshot) {
                  inspect(snapshot);
                  log('sasasa');
                  if (snapshot.hasData) {
                    return Text('QR code: ${snapshot.data}');
                  }
                  return const Text('No QR code');
                },
              )*/
            ],
          )),
    );
  }
}
