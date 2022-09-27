import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'scanner_method_channel.dart';

abstract class ScannerPlatform extends PlatformInterface {
  /// Constructs a ScannerPlatform.
  ScannerPlatform() : super(token: _token);

  static final Object _token = Object();

  static ScannerPlatform _instance = MethodChannelScanner();

  /// The default instance of [ScannerPlatform] to use.
  ///
  /// Defaults to [MethodChannelScanner].
  static ScannerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [ScannerPlatform] when
  /// they register themselves.
  static set instance(ScannerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> initScanner() =>
      throw UnimplementedError('initScanner() has not been implemented.');

  Future<String?> openScanner() =>
      throw UnimplementedError('openScanner() has not been implemented.');

  Future<void> releaseScanner() =>
      throw UnimplementedError('releaseScanner() has not been implemented.');

  Future<String?> getProps() =>
      throw UnimplementedError('getProps() has not been implemented.');
}
