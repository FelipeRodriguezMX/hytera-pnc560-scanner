
// class MockHyteraPlatform 
//     with MockPlatformInterfaceMixin
//     implements HyteraPlatform {

//   @override
//   Future<String?> getPlatformVersion() => Future.value('42');
// }

// void main() {
//   final HyteraPlatform initialPlatform = HyteraPlatform.instance;

//   test('$MethodChannelHytera is the default instance', () {
//     expect(initialPlatform, isInstanceOf<MethodChannelHytera>());
//   });

//   test('getPlatformVersion', () async {
//     Hytera hyteraPlugin = Hytera();
//     MockHyteraPlatform fakePlatform = MockHyteraPlatform();
//     HyteraPlatform.instance = fakePlatform;
  
//     expect(await hyteraPlugin.getPlatformVersion(), '42');
//   });
// }
