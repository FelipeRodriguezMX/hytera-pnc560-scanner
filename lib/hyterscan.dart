import 'hyterscan_platform_interface.dart';

class Hyterscan {
  Future<dynamic> scan() async => await HyterscanPlatform.instance.scan();

  Future<void> release() async => await HyterscanPlatform.instance.release();

  Future<bool?> get isInitialized async =>
      await HyterscanPlatform.instance.hasInstance;
}
