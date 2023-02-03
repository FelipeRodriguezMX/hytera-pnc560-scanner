# Hytera PNC 560 Scanner Plugin

A new Flutter plugin for the device Hytera PNC 560 which allows to use it scanner functionality.

## Handling errors

## Example

Here is an example of how to use the plugin:

```dart

import 'package:hyterscan/hyterscan.dart';



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
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Text('Scanner props: $_scannerProps'),
            TextButton(
              onPressed: () async {
                try {
                  final result = await _hyteraPlugin.scan();
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

```
