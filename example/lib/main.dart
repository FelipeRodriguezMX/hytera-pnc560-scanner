import 'dart:async';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:hyterscan/hyterscan.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _hyteraPlugin = Hyterscan();
  String? _scannerProps;
  @override
  void initState() {
    super.initState();
    init();
  }

  Future<void> init() async {
    await _hyteraPlugin.init();
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
                  await _hyteraPlugin.scan();
                } catch (e) {
                  inspect(e);
                }
              },
              child: const Text('Open'),
            ),
            TextButton(
              onPressed: () async {
                try {
                  await _hyteraPlugin.close();
                } catch (e) {
                  inspect(e);
                }
              },
              child: const Text('Close'),
            ),
            TextButton(
              onPressed: () async {
                try {
                  final props = await _hyteraPlugin.getProps();
                  inspect(props);
                  setState(() => _scannerProps = props);
                } catch (e) {
                  inspect(e);
                }
              },
              child: const Text('Get props'),
            ),
          ],
        ),
      ),
    );
  }
}
