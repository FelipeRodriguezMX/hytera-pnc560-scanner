import 'hyterscan_platform_interface.dart';

class Hyterscan {
  Future<void> init() async => await HyterscanPlatform.instance.init();

  Future<int?> scan() async => await HyterscanPlatform.instance.scan();

  Future<void> close() async => await HyterscanPlatform.instance.close();

  Future<String?> getProps() async =>
      await HyterscanPlatform.instance.getProps();

  Stream<String>? get scanStream => HyterscanPlatform.instance.scanStream;
}
