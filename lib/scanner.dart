
import 'scanner_platform_interface.dart';

class Scanner {
  Future<int?> initScanner() =>
    ScannerPlatform.instance.initScanner();

  Future<int?> openScanner() =>
      ScannerPlatform.instance.openScanner();

  Future<void> releaseScanner() =>
      ScannerPlatform.instance.releaseScanner();

  Future<String?> getProps() =>
      ScannerPlatform.instance.getProps();
}
