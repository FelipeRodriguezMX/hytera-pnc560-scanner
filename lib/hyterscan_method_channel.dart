import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'hyterscan_platform_interface.dart';

/// An implementation of [HyterscanPlatform] that uses method channels.
class MethodChannelHyterscan extends HyterscanPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  static const methodChannel = MethodChannel('hyteraScanner');
  static const eventChannel = EventChannel('hyteraScannerStream');

  @override
  Future<void> close() async => await methodChannel.invokeMethod('close');

  @override
  Future<void> release() async => await methodChannel.invokeMethod('release');

  @override
  Future<String?> getProps() async =>
      await methodChannel.invokeMethod<String?>('getProps');

  @override
  Future<void> init() async => await methodChannel.invokeMethod<void>('init');

  @override
  Future<int?> scan() async => await methodChannel.invokeMethod<int?>('scan');

  @override
  Stream<String?> get scanStream =>
      eventChannel.receiveBroadcastStream().map<String>((event) => event);
}
