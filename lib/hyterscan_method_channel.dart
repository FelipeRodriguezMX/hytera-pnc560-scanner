import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'hyterscan_platform_interface.dart';

/// An implementation of [HyterscanPlatform] that uses method channels.
class MethodChannelHyterscan extends HyterscanPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  static const methodChannel = MethodChannel('scanner');

  @override
  Future<void> release() async => await methodChannel.invokeMethod('release');

  @override
  Future<dynamic> scan() async =>
      await methodChannel.invokeMethod<int?>('scan');

  @override
  Future<bool?> get hasInstance async =>
      await methodChannel.invokeMethod<bool>('hasInstances');
}
