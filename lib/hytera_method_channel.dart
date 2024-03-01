import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'hytera_platform_interface.dart';

class MethodChannelHytera extends HyteraPlatform {
  @visibleForTesting
  static const methodChannel = MethodChannel('hyteraScanner');
  static const eventChannel = EventChannel('hyteraScannerStream');
  Stream<String>? _scanStream;

  @override
  Future<void> close() async => await methodChannel.invokeMethod('close');

  @override
  Future<String?> getProps() async =>
      await methodChannel.invokeMethod<String?>('getProps');

  @override
  Future<void> init() async => await methodChannel.invokeMethod('init');

  @override
  Future<int?> scan() async => await methodChannel.invokeMethod<int?>('scan');

  @override
  Stream<String>? get scanStream => _scanStream ??=
      eventChannel.receiveBroadcastStream().map((event) => event);
}
