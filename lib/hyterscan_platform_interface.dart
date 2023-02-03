import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'hyterscan_method_channel.dart';

abstract class HyterscanPlatform extends PlatformInterface {
  HyterscanPlatform() : super(token: _token);

  static final Object _token = Object();

  static HyterscanPlatform _instance = MethodChannelHyterscan();

  static HyterscanPlatform get instance => _instance;

  static set instance(HyterscanPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<void> init();

  Future<int?> scan();

  Future<void> close();

  Future<void> release();

  Future<String?> getProps();

  Stream<String?> get scanStream;
}
