import 'hytera_platform_interface.dart';

class Hytera {
  Future<void> init() async => await HyteraPlatform.instance.init();

  Future<int?> scan() async => await HyteraPlatform.instance.scan();

  Future<void> close() async => await HyteraPlatform.instance.close();

  Future<String?> getProps() async => await HyteraPlatform.instance.getProps();

  Stream<String>? get scanStream => HyteraPlatform.instance.scanStream;
}
