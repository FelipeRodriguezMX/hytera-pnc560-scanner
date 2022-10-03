import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'scanner_method_channel.dart';

abstract class ScannerPlatform extends PlatformInterface {
  ScannerPlatform() : super(token: _token);

  static final Object _token = Object();

  static ScannerPlatform _instance = MethodChannelScanner();

  static ScannerPlatform get instance => _instance;

  static set instance(ScannerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<int?> init();

  Future<int?> initScanner();

  Future<int?> openScanner();

  Future<void> closeScanner();

  Future<void> releaseScanner();

  Future<String?> getProps();

  Future<String?> getParams({required String parameterNumber});

  Future<int?> setParams(
      {required String parameterNumber, required String value});

  Stream<dynamic>? get qrCodeStream;
}
