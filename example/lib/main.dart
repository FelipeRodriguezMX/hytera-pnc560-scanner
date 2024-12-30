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
  dynamic initError;
  dynamic scanError;
  dynamic releaseError;
  dynamic hasInstanceError;

  Future<void> scan() async {
    try {
      final result = await _hyteraPlugin.scan();
      log(result);
    } catch (e) {
      setState(() => initError = e);
    }
  }

  Future<void> init() async {
    try {
      await _hyteraPlugin.init();
    } catch (e) {
      setState(() => scanError = e);
    }
  }

  Future<void> release() async {
    try {
      await _hyteraPlugin.init();
    } catch (e) {
      setState(() => releaseError = e);
    }
  }

  Future<void> hasInstance() async {
    try {
      await _hyteraPlugin.release();
    } catch (e) {
      setState(() => hasInstanceError = e);
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Hytera Scanner example app'),
        ),
        body: Column(
          children: [
            TextButton(
              onPressed: () async => init(),
              child: const Text('Init'),
            ),
            TextButton(
              onPressed: () async => scan(),
              child: const Text('Scan'),
            ),
            TextButton(
              onPressed: () async => await release(),
              child: const Text('Release'),
            ),
            TextButton(
              onPressed: () async => hasInstance(),
              child: const Text('Check instance'),
            ),
            Text('Error: $initError'),
            Text('Error: $scanError'),
            Text('Error: $releaseError'),
            Text('Error: $hasInstanceError'),
          ],
        ),
      ),
    );
  }
}
