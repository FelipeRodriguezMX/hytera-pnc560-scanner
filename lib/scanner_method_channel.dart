import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'scanner_platform_interface.dart';

/// An implementation of [ScannerPlatform] that uses method channels.
class MethodChannelScanner extends ScannerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('scanner');

  @override
  Future<int?> initScanner() async =>
     await methodChannel.invokeMethod<int?>('init');

  @override
  Future<int?> openScanner() async =>
      await methodChannel.invokeMethod<int?>('open');

  @override
  Future<void> releaseScanner() async =>
      await methodChannel.invokeMethod<int?>('release');

  @override
  Future<String?> getProps() async =>
      await methodChannel.invokeMethod<String?>('getProps');


}
