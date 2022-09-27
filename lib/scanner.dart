
import 'scanner_platform_interface.dart';

class Scanner {
  Future<String?> getPlatformVersion() {
    return ScannerPlatform.instance.getPlatformVersion();
  }
}
