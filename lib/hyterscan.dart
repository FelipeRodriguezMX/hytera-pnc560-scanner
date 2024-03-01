import 'hyterscan_platform_interface.dart';

class Hyterscan {
  Future<dynamic> scan() async => await HyterscanPlatform.instance.scan();

  Future<void> release() async => await HyterscanPlatform.instance.release();

  Future<bool?> hasInstance() async =>
      await HyterscanPlatform.instance.hasInstance();

  Future<void> init() async => await HyterscanPlatform.instance.init();
}
