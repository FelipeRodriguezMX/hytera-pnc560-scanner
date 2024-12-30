# Hytera PNC 560 Scanner Plugin

A new Flutter plugin for the device Hytera PNC 560 which allows to use it scanner functionality.

## Include package to app
Their are 2 ways to use the package.

This requires to clone repo and add it in pubspec.yaml file
```
hyterscan:
    path: {$path of cloned repo}/hyterscan
```
Or by Github 
```
 git:
      url: https://github.com/jlouage/flutter-carousel-pro.git
      ref: main
```

The package implements the following methods:

## How to use 
To use package in app first import it.
```
import 'package:hyterscan/hyterscan.dart';
```
Then implement it as the following:
```
final hyteraScan = Hyterscan();

await hyteraScan.init();

await hyteraScan.scan();

await hyteraScan.hasInstance();

await hyteraScan.init();

```

### release
Releases the scanner service when it is no longer needed.
Return Value: A Future<void> that completes when the scanner service has been released.

### scan
Initiates a scan using the Hytera PNC560 scanner.
Return Value: A Future<dynamic> that completes with the result of the scan operation.

### hasInstance
Checks if an instance of the scanner service is available.
Return Value: A Future<bool?> that completes with true if an instance of the scanner service is available, false if it is not available, or null if the check failed.

### init
Initializes the scanner service.
Return Value: A Future<void> that completes when the scanner service has been initialized.


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
The files inside of `android/src/libs/` is a proprietary and provided by Hytera Communications Corporation Limited for specific hardware functionality. This file:
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
