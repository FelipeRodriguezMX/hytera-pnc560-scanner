import 'scanner_platform_interface.dart';

class Scanner {
  Future<String?> initScanner() => ScannerPlatform.instance.initScanner();

  Future<String?> openScanner() => ScannerPlatform.instance.openScanner();

  Future<void> releaseScanner() => ScannerPlatform.instance.releaseScanner();

  Future<String?> getProps() => ScannerPlatform.instance.getProps();
}
