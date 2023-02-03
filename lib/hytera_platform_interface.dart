import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'hytera_method_channel.dart';

abstract class HyteraPlatform extends PlatformInterface {
  HyteraPlatform() : super(token: _token);

  static final Object _token = Object();

  static HyteraPlatform _instance = MethodChannelHytera();

  static HyteraPlatform get instance => _instance;

  static set instance(HyteraPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<void> init();

  Future<int?> scan();

  Future<void> close();

  Future<String?> getProps();

  Stream<String>? get scanStream;
}
