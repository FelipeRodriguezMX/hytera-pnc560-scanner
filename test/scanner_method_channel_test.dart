import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:scanner/scanner_method_channel.dart';

void main() {
  MethodChannelScanner platform = MethodChannelScanner();
  const MethodChannel channel = MethodChannel('scanner');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
