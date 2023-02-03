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
    _hyteraPlugin.init();
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
            Text('Scanner props: $_scannerProps'),
            TextButton(
              onPressed: () async {
                try {
                  await _hyteraPlugin.scan();
                } catch (e) {
                  inspect(e);
                }
              },
              child: const Text('Scan'),
            ),
            TextButton(
              onPressed: () async => await _hyteraPlugin.close(),
              child: const Text('Close'),
            ),
            TextButton(
              onPressed: () async {
                try {
                  final props = await _hyteraPlugin.getProps();
                  setState(() => _scannerProps = props);
                } catch (e) {
                  inspect(e);
                }
              },
              child: const Text('Props'),
            ),
            StreamBuilder(
              stream: _hyteraPlugin.scanStream,
              builder: (context, snapshot) => Text('Stream: ${snapshot.data}'),
            ),
          ],
        ),
      ),
    );
  }
}
