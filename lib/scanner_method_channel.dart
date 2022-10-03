import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'scanner_platform_interface.dart';

/// An implementation of [ScannerPlatform] that uses method channels.
class MethodChannelScanner extends ScannerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  static const methodChannel = MethodChannel('scanner');
  static const eventChannel = EventChannel('scannerStream');
  Stream<String>? _qrCodeStream;

  @override
  Future<int?> init() async => await methodChannel.invokeMethod<int?>('init');

  @override
  Future<int?> initScanner() async =>
      await methodChannel.invokeMethod<int?>('initScanner');

  @override
  Future<int?> openScanner() async =>
      await methodChannel.invokeMethod<int?>('start');

  @override
  Future<String?> getProps() async =>
      await methodChannel.invokeMethod<String?>('getProps');

  @override
  Future<String?> getParams({
    required String parameterNumber,
  }) async =>
      await methodChannel.invokeMethod<String?>('getParams', {
        'parameterNumber': parameterNumber,
      });

  @override
  Future<int?> setParams({
    required String parameterNumber,
    required String value,
  }) async =>
      await methodChannel.invokeMethod<int?>('setParams', {
        'parameterNumber': parameterNumber,
        'value': value,
      });

  @override
  Future<void> closeScanner() async =>
      await methodChannel.invokeMethod<int?>('close');

  @override
  Future<void> releaseScanner() async =>
      await methodChannel.invokeMethod<int?>('release');

  @override
  Stream<String>? get qrCodeStream => _qrCodeStream ??=
      eventChannel.receiveBroadcastStream().map<String>((event) => event);
}
