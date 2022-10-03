import 'scanner_platform_interface.dart';

class Scanner {
  Future<int?> init() => ScannerPlatform.instance.init();

  Future<int?> initScanner() => ScannerPlatform.instance.initScanner();

  Future<int?> openScanner() => ScannerPlatform.instance.openScanner();

  Future<void> closeScanner() => ScannerPlatform.instance.closeScanner();

  Future<void> releaseScanner() => ScannerPlatform.instance.releaseScanner();

  Future<String?> getProps() => ScannerPlatform.instance.getProps();

  Future<String?> getParams({required String parameterNumber}) =>
      ScannerPlatform.instance.getParams(parameterNumber: parameterNumber);

  Future<int?> setParams(
          {required String parameterNumber, required String value}) =>
      ScannerPlatform.instance
          .setParams(parameterNumber: parameterNumber, value: value);

  Stream<dynamic>? get qrCodeStream => ScannerPlatform.instance.qrCodeStream;
}
