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

  @override
  void initState() {
    super.initState();
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
              onPressed: () async {
                try {
                  await _hyteraPlugin.init();
                } catch (e) {
                  inspect(e);
                }
              },
              child: const Text('Init'),
            ),
            TextButton(
              onPressed: () async {
                try {
                  final result = await _hyteraPlugin.scan();
                  inspect(result);
                } catch (e) {
                  inspect(e);
                }
              },
              child: const Text('Scan'),
            ),
            TextButton(
              onPressed: () async => await _hyteraPlugin.release(),
              child: const Text('Release'),
            ),
            TextButton(
              onPressed: () async {
                final result = await _hyteraPlugin.hasInstance();
                inspect(result);
              },
              child: const Text('Check instance'),

            ),
          ],
        ),
      ),
    );
  }
}
