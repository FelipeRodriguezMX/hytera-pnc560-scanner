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

## Licensing

### Project Licenses
This project is dual-licensed under two open-source licenses:

1. **BSD-3-Clause License**
   - Allows redistribution and use in source and binary forms
   - Requires preservation of the original copyright notice
   - Provides a disclaimer of liability

2. **MIT License**
   - Permits use, copying, modification, merging, publishing, distribution
   - Requires inclusion of the original copyright notice
   - Provides a disclaimer of liability

You may choose to use this project under the terms of either the BSD-3-Clause or MIT license.

### Third-Party Software Exception

#### Hytera libscanner.jar
The file `android/src/libs/libscanner.jar` is a proprietary library provided by Hytera for specific hardware functionality. This file:
- Is NOT covered by the project's main licenses
- Remains the exclusive property of Hytera
- Is used with permission for device-specific hardware interactions

### Full License Texts
For the complete text of the BSD-3-Clause and MIT licenses, please refer to the LICENSE file for BSD-3 and LICENSE.md for MIT in the project root.

### Usage and Redistribution
When redistributing this project, you must:
- Preserve all copyright notices
- Include the original licenses
- Clearly distinguish between project code and the Hytera proprietary library
