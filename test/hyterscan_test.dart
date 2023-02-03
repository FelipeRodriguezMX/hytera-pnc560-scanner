// import 'package:flutter_test/flutter_test.dart';
// import 'package:hyterscan/hyterscan.dart';
// import 'package:hyterscan/hyterscan_platform_interface.dart';
// import 'package:hyterscan/hyterscan_method_channel.dart';
// import 'package:plugin_platform_interface/plugin_platform_interface.dart';

// class MockHyterscanPlatform 
//     with MockPlatformInterfaceMixin
//     implements HyterscanPlatform {

//   @override
//   Future<String?> getPlatformVersion() => Future.value('42');
// }

// void main() {
//   final HyterscanPlatform initialPlatform = HyterscanPlatform.instance;

//   test('$MethodChannelHyterscan is the default instance', () {
//     expect(initialPlatform, isInstanceOf<MethodChannelHyterscan>());
//   });

//   test('getPlatformVersion', () async {
//     Hyterscan hyterscanPlugin = Hyterscan();
//     MockHyterscanPlatform fakePlatform = MockHyterscanPlatform();
//     HyterscanPlatform.instance = fakePlatform;
  
//     expect(await hyterscanPlugin.getPlatformVersion(), '42');
//   });
// }
